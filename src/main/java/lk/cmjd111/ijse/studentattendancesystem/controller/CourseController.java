package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import lk.cmjd111.ijse.studentattendancesystem.dao.CourseDAO;
import lk.cmjd111.ijse.studentattendancesystem.dao.impl.CourseImpl;
import lk.cmjd111.ijse.studentattendancesystem.model.Course;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CourseController {
    @FXML private TextField txtCourseId;
    @FXML private TextField txtCourseName;
    @FXML private TextArea txtDescription;
    @FXML private TextField txtDuration;
    @FXML private TableView<Course> tblCourses;
    @FXML private TableColumn<Course, String> colCourseId;
    @FXML private TableColumn<Course, String> colCourseName;
    @FXML private TableColumn<Course, String> colDescription;
    @FXML private TableColumn<Course, String> colDuration;

    private final CourseDAO courseDAO = CourseImpl.getInstance();
    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colCourseId.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());
        colCourseName.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colDuration.setCellValueFactory(cellData -> cellData.getValue().durationProperty());

        loadAllCourses();
    }

    private void loadAllCourses() {
        try {
            List<Course> allCourses = courseDAO.getAllCourses();
            courseList.setAll(allCourses);
            tblCourses.setItems(courseList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleAddCourse() {
        String id = txtCourseId.getText().trim();
        String name = txtCourseName.getText().trim();
        String description = txtDescription.getText().trim();
        String duration = txtDuration.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Course ID and Name are required.");
            return;
        }

        Course course = new Course(id, name, description, duration);

        try {
            boolean success = courseDAO.addCourse(course);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully.");
                loadAllCourses();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to add course.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateCourse() {
        String id = txtCourseId.getText().trim();
        String name = txtCourseName.getText().trim();
        String description = txtDescription.getText().trim();
        String duration = txtDuration.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Course ID is required.");
            return;
        }

        Course course = new Course(id, name, description, duration);

        try {
            boolean success = courseDAO.updateCourse(course);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully.");
                loadAllCourses();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to update course.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteCourse() {
        String id = txtCourseId.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Course ID is required.");
            return;
        }

        try {
            boolean success = courseDAO.deleteCourse(id);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course deleted successfully.");
                loadAllCourses();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to delete course.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleSearchCourse() {
        String id = txtCourseId.getText().trim();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Course ID is required for search.");
            return;
        }

        try {
            Course course = courseDAO.searchCourse(id);
            if (course != null) {
                txtCourseId.setText(course.getCourseId());
                txtCourseName.setText(course.getCourseName());
                txtDescription.setText(course.getDescription());
                txtDuration.setText(course.getDuration());
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "Course not found.");
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

    private void clearFields() {
        txtCourseId.clear();
        txtCourseName.clear();
        txtDescription.clear();
        txtDuration.clear();
    }
    @FXML
    private void handleBack() {
        try {
            java.net.URL resource = getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/admin_dashboard.fxml");
            if (resource == null) {
                System.out.println("FXML resource not found!");
                return;
            } else {
                System.out.println("FXML resource found at: " + resource);
            }

            Stage stage = (Stage) txtCourseId.getScene().getWindow();
            Parent root = FXMLLoader.load(resource);


            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin Dashboard");
            stage.show();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load admin dashboard: " + e.getMessage());
        }
    }



    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}