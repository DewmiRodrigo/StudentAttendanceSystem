package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.cmjd111.ijse.studentattendancesystem.dao.AttendanceDAO;
import lk.cmjd111.ijse.studentattendancesystem.model.AttendanceRecord;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.List;

public class ReportController {

    @FXML
    private ComboBox<String> studentFilterCombo;

    @FXML
    private ComboBox<String> subjectFilterCombo;

    @FXML
    private TextArea reportTextArea;

    @FXML
    public void initialize() {
        try {
            // Load students and subjects into combo boxes
            studentFilterCombo.getItems().addAll(AttendanceDAO.getAllStudents());
            subjectFilterCombo.getItems().addAll(AttendanceDAO.getAllSubjects());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onFilterByStudent(ActionEvent event) {
        updateReport();
    }

    @FXML
    private void onFilterBySubject(ActionEvent event) {
        updateReport();
    }

    @FXML
    private void onClearFilters() {
        studentFilterCombo.getSelectionModel().clearSelection();
        subjectFilterCombo.getSelectionModel().clearSelection();
        updateReport();
    }

    private void updateReport() {
        String student = studentFilterCombo.getValue();
        String subject = subjectFilterCombo.getValue();

        try {
            List<AttendanceRecord> records = AttendanceDAO.getFilteredAttendance(student, subject);


            reportTextArea.clear();

            if (records.isEmpty()) {
                reportTextArea.setText("No attendance records found for the selected filters.");
            } else {
                for (AttendanceRecord r : records) {
                    String line = String.format("%s | %s | %s | %s\n",
                            r.getStudentName(),
                            r.getSubjectName(),
                            r.getDate(),
                            r.getStatus());
                    reportTextArea.appendText(line);
                }
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
        fileChooser.setTitle("Save Attendance Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            } catch (Exception e) {
                e.printStackTrace();
                reportTextArea.setText("Error saving file.");
            }
        }
    }
}
