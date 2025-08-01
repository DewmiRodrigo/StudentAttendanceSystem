package lk.cmjd111.ijse.studentattendancesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Student Attendance System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}