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


        final String ADMIN_USER = "admin";
        final String ADMIN_PASS = "admin123";
        final String LECTURER_USER = "lecturer";
        final String LECTURER_PASS = "lecturer123";

        if (username.equals(ADMIN_USER) && password.equals(ADMIN_PASS)) {
            loadDashboard("/lk/cmjd111/ijse/studentattendancesystem/admin_dashboard.fxml");
        }
        else if (username.equals(LECTURER_USER) && password.equals(LECTURER_PASS)) {
            loadDashboard("/lk/cmjd111/ijse/studentattendancesystem/lecturer_dashboard.fxml");
        }
        else {
            showAlert("Login Failed", "Invalid username or password!");
            passwordField.clear();
        }
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