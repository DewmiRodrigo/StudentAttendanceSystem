package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.cmjd111.ijse.studentattendancesystem.dao.AttendanceDAO;
import java.sql.SQLException;
import java.time.LocalDate;

public class AttendanceController {
    @FXML private ComboBox<String> cmbStudent;
    @FXML private ComboBox<String> cmbCourse;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private DatePicker datePicker;

    public void initialize() {
        try {

            cmbStudent.getItems().addAll(AttendanceDAO.getAllStudents());
            cmbCourse.getItems().addAll(AttendanceDAO.getAllCourses());


            datePicker.setValue(LocalDate.now());
            cmbStatus.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            showAlert("Error", "Failed to load data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmit() {
        try {

            if (cmbStudent.getValue() == null || cmbStudent.getValue().isEmpty()) {
                showAlert("Error", "Please select a student");
                return;
            }

            if (cmbCourse.getValue() == null || cmbCourse.getValue().isEmpty()) {
                showAlert("Error", "Please select a course");
                return;
            }

            if (datePicker.getValue() == null) {
                showAlert("Error", "Please select a date");
                return;
            }


            boolean isSaved = AttendanceDAO.markAttendance(
                    cmbStudent.getValue(),
                    cmbCourse.getValue(),
                    datePicker.getValue().toString(),
                    cmbStatus.getValue()
            );

            if (isSaved) {
                showAlert("Success", "Attendance recorded for:\n" +
                        "Student: " + cmbStudent.getValue() + "\n" +
                        "Course: " + cmbCourse.getValue() + "\n" +
                        "Date: " + datePicker.getValue() + "\n" +
                        "Status: " + cmbStatus.getValue());


                cmbStatus.getSelectionModel().clearSelection();
                datePicker.setValue(LocalDate.now());
            } else {
                showAlert("Error", "Failed to save attendance");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to save: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}