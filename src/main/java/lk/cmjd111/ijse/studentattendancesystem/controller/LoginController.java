package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;


    @FXML
    private void handleLogin() throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();


        if (username.equals("admin") && password.equals("admin123")) {
            Parent root = FXMLLoader.load(getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/admin_dashboard.fxml"));
            loginButton.getScene().setRoot(root);
            return;
        }


        if (username.equals("lecturer") && password.equals("lecturer123")) {
            Parent root = FXMLLoader.load(getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/lecturer_dashboard.fxml"));
            loginButton.getScene().setRoot(root);
            return;
        }


        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Wrong username or password!");
        alert.showAndWait();


        passwordField.clear();
    }

    private void loadDashboard(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        loginButton.getScene().setRoot(root);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}