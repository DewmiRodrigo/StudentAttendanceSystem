package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LecturerDashboardController {

    @FXML
    private Button btnViewReports;
    @FXML
    private Button btnBackLecturer;

    @FXML
    private void goBackToLogin() throws IOException {
        loadView("/lk/cmjd111/ijse/studentattendancesystem/login.fxml", "Login");
    }

    private void loadView(String fxmlPath, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) btnBackLecturer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
    }

    @FXML
    private void handleMarkAttendance() {
        try {
            URL fxmlUrl = getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/attendance_mark.fxml");
            if (fxmlUrl == null) {
                throw new RuntimeException("Attendance marking FXML file not found!");
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mark Course Attendance");
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to open attendance marking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("btnViewReports initialized? " + (btnViewReports != null));
    }

    @FXML
    private void handleViewReports(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/reports_view.fxml");
            if (fxmlUrl == null) {
                throw new RuntimeException("Reports FXML file not found!");
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Course Attendance Reports");
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load course reports: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
