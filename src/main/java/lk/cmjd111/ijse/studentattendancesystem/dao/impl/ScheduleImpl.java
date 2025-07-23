package lk.cmjd111.ijse.studentattendancesystem.dao.impl;

import lk.cmjd111.ijse.studentattendancesystem.dao.ScheduleDAO;
import lk.cmjd111.ijse.studentattendancesystem.db.DBConnection;
import lk.cmjd111.ijse.studentattendancesystem.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleImpl implements ScheduleDAO {
    private static final ScheduleDAO scheduleDAO = new ScheduleImpl();

    private ScheduleImpl() {}

    public static ScheduleDAO getInstance() {
        return scheduleDAO;
    }

    @Override
    public boolean saveSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule (course_name, lecturer_name, class_date, start_time, end_time, hall_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, schedule.getCourseName());
            stmt.setString(2, schedule.getLecturerName());
            stmt.setString(3, schedule.getClassDate());
            stmt.setString(4, schedule.getStartTime());
            stmt.setString(5, schedule.getEndTime());
            stmt.setString(6, schedule.getHallNumber());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateSchedule(Schedule schedule) throws SQLException {
        String sql = "UPDATE schedule SET course_name = ?, lecturer_name = ?, class_date = ?, start_time = ?, end_time = ?, hall_number = ? WHERE schedule_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, schedule.getCourseName());
            stmt.setString(2, schedule.getLecturerName());
            stmt.setString(3, schedule.getClassDate());
            stmt.setString(4, schedule.getStartTime());
            stmt.setString(5, schedule.getEndTime());
            stmt.setString(6, schedule.getHallNumber());
            stmt.setString(7, schedule.getScheduleId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteSchedule(String scheduleId) throws SQLException {
        String sql = "DELETE FROM schedule WHERE schedule_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scheduleId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Schedule findSchedule(String scheduleId) throws SQLException {
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, scheduleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Schedule(
                            rs.getString("schedule_id"),
                            rs.getString("course_name"),
                            rs.getString("lecturer_name"),
                            rs.getString("class_date"),
                            rs.getString("start_time"),
                            rs.getString("end_time"),
                            rs.getString("hall_number")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Schedule> getAllSchedules() throws SQLException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedule";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                schedules.add(new Schedule(
                        rs.getString("schedule_id"),
                        rs.getString("course_name"),
                        rs.getString("lecturer_name"),
                        rs.getString("class_date"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("hall_number")
                ));
            }
        }
        return schedules;
    }

    @Override
    public List<String> getAllCourseNames() throws SQLException {
        List<String> courseNames = new ArrayList<>();
        String sql = "SELECT course_name FROM courses";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courseNames.add(rs.getString("course_name"));
            }
        }
        return courseNames;
    }

    @Override
    public List<String> getAllLecturerNames() throws SQLException {
        List<String> lecturerNames = new ArrayList<>();
        String sql = "SELECT full_name FROM lecturers";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lecturerNames.add(rs.getString("full_name"));
            }
        }
        return lecturerNames;
    }
}
