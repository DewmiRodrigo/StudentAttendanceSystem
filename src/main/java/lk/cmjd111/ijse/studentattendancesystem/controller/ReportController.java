package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.cmjd111.ijse.studentattendancesystem.dao.AttendanceDAO;
import lk.cmjd111.ijse.studentattendancesystem.model.AttendanceRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReportController {

    @FXML
    private ComboBox<String> studentFilterCombo;

    @FXML
    private ComboBox<String> courseFilterCombo;

    @FXML
    private TextArea reportTextArea;

    @FXML
    public void initialize() {
        try {
            studentFilterCombo.getItems().addAll(AttendanceDAO.getAllStudents());
            courseFilterCombo.getItems().addAll(AttendanceDAO.getAllCourses());
        } catch (SQLException e) {
            e.printStackTrace();
            reportTextArea.setText("Error loading student/course data.");
        }
    }

    @FXML
    private void onFilterByStudent(ActionEvent event) {
        updateReport();
    }

    @FXML
    private void onFilterByCourse(ActionEvent event) {
        updateReport();
    }

    @FXML
    private void onClearFilters() {
        studentFilterCombo.getSelectionModel().clearSelection();
        courseFilterCombo.getSelectionModel().clearSelection();
        updateReport();
    }

    private void updateReport() {
        String student = studentFilterCombo.getValue();
        String course = courseFilterCombo.getValue();

        try {
            List<AttendanceRecord> records = AttendanceDAO.getFilteredAttendance(student, course);
            reportTextArea.clear();

            if (records.isEmpty()) {
                reportTextArea.setText("No attendance records found for the selected filters.");
            } else {
                StringBuilder reportBuilder = new StringBuilder();
                reportBuilder.append("STUDENT NAME | COURSE NAME | DATE | STATUS\n");
                reportBuilder.append("------------------------------------------\n");

                for (AttendanceRecord r : records) {
                    String line = String.format("%-15s | %-15s | %-10s | %-7s\n",
                            r.getStudentName(),
                            r.getCourseName(),
                            r.getDate(),
                            r.getStatus());
                    reportBuilder.append(line);
                }
                reportTextArea.setText(reportBuilder.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            reportTextArea.setText("Error loading attendance records.");
        }
    }

    @FXML
    private void handleExport() {
        String content = reportTextArea.getText();
        if (content == null || content.isEmpty()) {
            reportTextArea.setText("No data to export!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Course Attendance Report");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
                reportTextArea.appendText("\n\nReport successfully exported to: " + file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                reportTextArea.setText("Error saving file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleBackLecturer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/lk/cmjd111/ijse/studentattendancesystem/lecturer_dashboard.fxml"
            ));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Lecturer Dashboard");
            stage.show();
        } catch (IOException e) {
            reportTextArea.setText("Navigation Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
