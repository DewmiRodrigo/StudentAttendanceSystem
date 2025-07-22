package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class LecturerDashboardController {

    @FXML
    private Button markAttendanceBtn;
    @FXML
    private Button viewStudentsBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    public void initialize() {
        // Button hover effects
        markAttendanceBtn.setOnMouseEntered(e -> markAttendanceBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #d3d3d3;"));
        markAttendanceBtn.setOnMouseExited(e -> markAttendanceBtn.setStyle("-fx-font-size: 14px;"));

        viewStudentsBtn.setOnMouseEntered(e -> viewStudentsBtn.setStyle("-fx-font-size: 14px; -fx-background-color: #d3d3d3;"));
        viewStudentsBtn.setOnMouseExited(e -> viewStudentsBtn.setStyle("-fx-font-size: 14px;"));
    }

    @FXML
    private void handleMarkAttendance() {
        showAlert("ry", "rg");
    }

    @FXML
    private void handleViewStudents() {
        showAlert("tr","tht");
    }

    @FXML
    private void handleLogout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/login.fxml"));
        logoutBtn.getScene().setRoot(root);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}