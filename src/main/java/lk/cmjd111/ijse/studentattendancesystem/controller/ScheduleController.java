package lk.cmjd111.ijse.studentattendancesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.cmjd111.ijse.studentattendancesystem.dao.ScheduleDAO;
import lk.cmjd111.ijse.studentattendancesystem.dao.impl.ScheduleImpl;
import lk.cmjd111.ijse.studentattendancesystem.model.Schedule;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ScheduleController {

    @FXML
    private ComboBox<String> cmbCourseName;
    @FXML
    private ComboBox<String> cmbLecturerName;
    @FXML
    private DatePicker dpClassDate;
    @FXML
    private TextField txtStartTime;
    @FXML
    private TextField txtEndTime;
    @FXML
    private TextField txtHallNumber;

    @FXML
    private TableView<Schedule> tblSchedule;
    @FXML
    private TableColumn<Schedule, String> colScheduleId;
    @FXML
    private TableColumn<Schedule, String> colCourseName;
    @FXML
    private TableColumn<Schedule, String> colLecturerName;
    @FXML
    private TableColumn<Schedule, String> colClassDate;
    @FXML
    private TableColumn<Schedule, String> colStartTime;
    @FXML
    private TableColumn<Schedule, String> colEndTime;
    @FXML
    private TableColumn<Schedule, String> colHallNumber;

    private final ScheduleDAO scheduleDAO = ScheduleImpl.getInstance();
    private ObservableList<Schedule> scheduleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colScheduleId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getScheduleId()));
        colCourseName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourseName()));
        colLecturerName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLecturerName()));
        colClassDate.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClassDate()));
        colStartTime.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStartTime()));
        colEndTime.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEndTime()));
        colHallNumber.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getHallNumber()));

        loadAllSchedules();
        loadCourseNames();
        loadLecturerNames();

        tblSchedule.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) setData(newSelection);
        });
    }

    private void loadAllSchedules() {
        try {
            List<Schedule> schedules = scheduleDAO.getAllSchedules();
            scheduleList.setAll(schedules);
            tblSchedule.setItems(scheduleList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void loadCourseNames() {
        try {
            List<String> courseNames = scheduleDAO.getAllCourseNames();
            cmbCourseName.getItems().setAll(courseNames);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load course names: " + e.getMessage());
        }
    }

    private void loadLecturerNames() {
        try {
            List<String> lecturerNames = scheduleDAO.getAllLecturerNames();
            cmbLecturerName.getItems().setAll(lecturerNames);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load lecturer names: " + e.getMessage());
        }
    }

    private void setData(Schedule schedule) {
        cmbCourseName.setValue(schedule.getCourseName());
        cmbLecturerName.setValue(schedule.getLecturerName());
        dpClassDate.setValue(java.time.LocalDate.parse(schedule.getClassDate()));
        txtStartTime.setText(schedule.getStartTime());
        txtEndTime.setText(schedule.getEndTime());
        txtHallNumber.setText(schedule.getHallNumber());
    }

    @FXML
    private void handleAddSchedule(ActionEvent event) {
        String courseName = cmbCourseName.getValue();
        String lecturerName = cmbLecturerName.getValue();
        if (dpClassDate.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Class date is required.");
            return;
        }
        String classDate = dpClassDate.getValue().toString();
        String startTime = txtStartTime.getText().trim();
        String endTime = txtEndTime.getText().trim();
        String hallNumber = txtHallNumber.getText().trim();

        if (courseName == null || lecturerName == null || startTime.isEmpty() || endTime.isEmpty() || hallNumber.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        Schedule schedule = new Schedule(null, courseName, lecturerName, classDate, startTime, endTime, hallNumber);

        try {
            boolean success = scheduleDAO.saveSchedule(schedule);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Schedule added successfully.");
                loadAllSchedules();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to add schedule.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateSchedule(ActionEvent event) {
        Schedule selected = tblSchedule.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Select a schedule to update.");
            return;
        }

        String courseName = cmbCourseName.getValue();
        String lecturerName = cmbLecturerName.getValue();
        if (dpClassDate.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Class date is required.");
            return;
        }
        String classDate = dpClassDate.getValue().toString();
        String startTime = txtStartTime.getText().trim();
        String endTime = txtEndTime.getText().trim();
        String hallNumber = txtHallNumber.getText().trim();

        if (courseName == null || lecturerName == null || startTime.isEmpty() || endTime.isEmpty() || hallNumber.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        Schedule schedule = new Schedule(selected.getScheduleId(), courseName, lecturerName, classDate, startTime, endTime, hallNumber);

        try {
            boolean success = scheduleDAO.updateSchedule(schedule);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Schedule updated successfully.");
                loadAllSchedules();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to update schedule.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteSchedule(ActionEvent event) {
        Schedule selected = tblSchedule.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Select a schedule to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this schedule?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean success = scheduleDAO.deleteSchedule(selected.getScheduleId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Schedule deleted successfully.");
                    loadAllSchedules();
                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "Failed to delete schedule.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleSearchSchedule(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Schedule");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter Schedule ID:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String scheduleId = result.get().trim();
            if (scheduleId.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Schedule ID is required for search.");
                return;
            }

            try {
                Schedule schedule = scheduleDAO.findSchedule(scheduleId);
                if (schedule != null) {
                    setData(schedule);
                    tblSchedule.getSelectionModel().select(schedule);
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Not Found", "Schedule not found.");
                    clearFields();
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }
        }
    }

    @FXML
    private void handleClearFields(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/lk/cmjd111/ijse/studentattendancesystem/admin_dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load admin dashboard: " + e.getMessage());
        }
    }

    private void clearFields() {
        cmbCourseName.getSelectionModel().clearSelection();
        cmbLecturerName.getSelectionModel().clearSelection();
        dpClassDate.setValue(null);
        txtStartTime.clear();
        txtEndTime.clear();
        txtHallNumber.clear();
        tblSchedule.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
