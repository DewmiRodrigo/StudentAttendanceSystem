package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LecturerDashboardController {
    @FXML
    private void handleMarkAttendance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                    "/lk/cmjd111/ijse/studentattendancesystem/attendance_mark.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mark Attendance");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}