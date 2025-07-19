package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import java.io.IOException;

public class LecturerDashboardController {
    @FXML private Button backButton;

    @FXML
    private void goBackToLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/login.fxml"));
        backButton.getScene().setRoot(root);
    }
}