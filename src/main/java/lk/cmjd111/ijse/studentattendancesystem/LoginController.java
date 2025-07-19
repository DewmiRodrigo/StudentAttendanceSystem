package lk.cmjd111.ijse.studentattendancesystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    public void initialize() {
        roleComboBox.getItems().add("Admin");
        roleComboBox.getItems().add("Lecturer");
    }

     public void handleLogin() {
        String role = roleComboBox.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (role == null || username.isEmpty() || password.isEmpty()) {
            showError("Error", "Please fill all fields!");
            return;
        }


        System.out.println("Login as: " + role + ", Username: " + username);
    }


    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}