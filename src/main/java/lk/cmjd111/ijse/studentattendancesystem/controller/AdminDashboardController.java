package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {
    @FXML private Button backButton;
    @FXML private Button btnManageCourses;
    @FXML private Button btnManageStudents;
    @FXML private Button btnManageLecturers;
    @FXML private Button btnManageSchedule;

    @FXML
    private void goBackToLogin() throws IOException {
        loadView("/lk/cmjd111/ijse/studentattendancesystem/login.fxml", "Login");
    }

    @FXML
    private void handleManageCourses() throws IOException {
        loadView("/lk/cmjd111/ijse/studentattendancesystem/courses_view.fxml", "Course Management");
    }

    @FXML
    private void handleManageStudents() throws IOException {
        loadView("/lk/cmjd111/ijse/studentattendancesystem/students_view.fxml", "Student Management");
    }

    @FXML
    private void handleManageLecturers() throws IOException {
        loadView("/lk/cmjd111/ijse/studentattendancesystem/lecturers_view.fxml", "Lecturer Management");
    }

    @FXML
    private void handleManageSchedule() throws IOException {
        loadView("/lk/cmjd111/ijse/studentattendancesystem/schedule_view.fxml", "Schedule Management");
    }

    private void loadView(String fxmlPath, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
