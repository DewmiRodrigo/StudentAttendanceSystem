package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.cmjd111.ijse.studentattendancesystem.dao.LecturerDAO;
import lk.cmjd111.ijse.studentattendancesystem.dao.impl.LecturerImpl;
import lk.cmjd111.ijse.studentattendancesystem.model.Lecturer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LecturerController {
    @FXML
    private TextField txtLecturerId;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> cmbCourseId;
    @FXML
    private TableView<Lecturer> tblLecturers;
    @FXML
    private TableColumn<Lecturer, String> colLecturerId;
    @FXML
    private TableColumn<Lecturer, String> colFullName;
    @FXML
    private TableColumn<Lecturer, String> colEmail;
    @FXML
    private TableColumn<Lecturer, String> colCourseId;
    @FXML
    private Button btnBack;

    private final LecturerDAO lecturerDAO = LecturerImpl.getInstance();
    private ObservableList<Lecturer> lecturerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colLecturerId.setCellValueFactory(cellData -> cellData.getValue().lecIdProperty());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colCourseId.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());

        loadAllLecturers();
        loadCourseIds();

        tblLecturers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setData(newSelection);
            }
        });
    }

    private void loadAllLecturers() {
        try {
            List<Lecturer> lecturers = lecturerDAO.getAllLecturers();
            lecturerList.setAll(lecturers);
            tblLecturers.setItems(lecturerList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void loadCourseIds() {
        try {
            List<String> courseIds = lecturerDAO.getAllCourseIds();
            cmbCourseId.getItems().setAll(courseIds);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load course IDs: " + e.getMessage());
        }
    }

    private void setData(Lecturer lecturer) {
        txtLecturerId.setText(lecturer.getLecId());
        txtFullName.setText(lecturer.getFullName());
        txtEmail.setText(lecturer.getEmail());
        cmbCourseId.setValue(lecturer.getCourseId());
    }

    @FXML
    private void handleAddLecturer(ActionEvent event) {
        String id = txtLecturerId.getText().trim();
        String name = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String courseId = cmbCourseId.getValue();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || courseId == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        Lecturer lecturer = new Lecturer(id, name, email, courseId);

        try {
            boolean success = lecturerDAO.saveLecturer(lecturer);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Lecturer added successfully.");
                loadAllLecturers();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to add lecturer.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateLecturer(ActionEvent event) {
        String id = txtLecturerId.getText().trim();
        String name = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String courseId = cmbCourseId.getValue();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Lecturer ID is required.");
            return;
        }

        Lecturer lecturer = new Lecturer(id, name, email, courseId);

        try {
            boolean success = lecturerDAO.updateLecturer(lecturer);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Lecturer updated successfully.");
                loadAllLecturers();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to update lecturer.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteLecturer(ActionEvent event) {
        String id = txtLecturerId.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Lecturer ID is required.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this lecturer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean success = lecturerDAO.deleteLecturer(id);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Lecturer deleted successfully.");
                    loadAllLecturers();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "Failed to delete lecturer.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleSearchLecturer(ActionEvent event) {
        String id = txtLecturerId.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Lecturer ID is required for search.");
            return;
        }

        try {
            Lecturer lecturer = lecturerDAO.findLecturer(id);
            if (lecturer != null) {
                setData(lecturer);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Lecturer not found.");
                clearFields();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleClearFields(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/admin_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load admin dashboard: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtLecturerId.clear();
        txtFullName.clear();
        txtEmail.clear();
        cmbCourseId.getSelectionModel().clearSelection();
        tblLecturers.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
