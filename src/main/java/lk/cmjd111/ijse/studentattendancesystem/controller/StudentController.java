package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.cmjd111.ijse.studentattendancesystem.dao.StudentDAO;
import lk.cmjd111.ijse.studentattendancesystem.dao.impl.StudentImpl;
import lk.cmjd111.ijse.studentattendancesystem.model.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentController {
    @FXML private TextField txtStudentId;
    @FXML private TextField txtFullName;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cmbCourseId;
    @FXML private TableView<Student> tblStudents;
    @FXML private TableColumn<Student, String> colStudentId;
    @FXML private TableColumn<Student, String> colFullName;
    @FXML private TableColumn<Student, String> colEmail;
    @FXML private TableColumn<Student, String> colCourseId;
    @FXML private Button btnBack;

    private final StudentDAO studentDAO = StudentImpl.getInstance();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colStudentId.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colCourseId.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());

        loadAllStudents();
        loadCourseIds();

        tblStudents.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setData(newSelection);
            }
        });
    }

    private void loadAllStudents() {
        try {
            List<Student> allStudents = studentDAO.getAllStudents();
            studentList.setAll(allStudents);
            tblStudents.setItems(studentList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void loadCourseIds() {
        try {
            cmbCourseId.getItems().setAll(studentDAO.getAllCourseIds());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load course IDs: " + e.getMessage());
        }
    }

    private void setData(Student student) {
        txtStudentId.setText(student.getStudentId());
        txtFullName.setText(student.getFullName());
        txtEmail.setText(student.getEmail());
        cmbCourseId.setValue(student.getCourseId());
    }

    @FXML
    private void handleAddStudent() {
        String id = txtStudentId.getText().trim();
        String name = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String courseId = cmbCourseId.getValue();

        if (id.isEmpty() || name.isEmpty() || courseId == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Student ID, Name, and Course ID are required.");
            return;
        }

        Student student = new Student(id, name, email, courseId);

        try {
            boolean success = studentDAO.saveStudent(student);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully.");
                loadAllStudents();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to add student.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateStudent() {
        String id = txtStudentId.getText().trim();
        String name = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String courseId = cmbCourseId.getValue();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Student ID is required.");
            return;
        }

        Student student = new Student(id, name, email, courseId);

        try {
            boolean success = studentDAO.updateStudent(student);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully.");
                loadAllStudents();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to update student.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteStudent() {
        String id = txtStudentId.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Student ID is required.");
            return;
        }

        try {
            boolean success = studentDAO.deleteStudent(id);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully.");
                loadAllStudents();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to delete student.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleSearchStudent() {
        String id = txtStudentId.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Student ID is required for search.");
            return;
        }

        try {
            Student student = studentDAO.findStudent(id);
            if (student != null) {
                setData(student);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Student not found.");
                clearFields();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleClearFields() {
        clearFields();
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/admin_dashboard.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clearFields() {
        txtStudentId.clear();
        txtFullName.clear();
        txtEmail.clear();
        cmbCourseId.getSelectionModel().clearSelection();
        tblStudents.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
