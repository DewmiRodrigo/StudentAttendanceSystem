package lk.cmjd111.ijse.studentattendancesystem.dao;

import lk.cmjd111.ijse.studentattendancesystem.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    public static List<String> getAllStudents() throws SQLException {
        List<String> students = new ArrayList<>();
        String sql = "SELECT student_id, full_name FROM students ORDER BY full_name";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(rs.getString("student_id") + " - " + rs.getString("full_name"));
            }
        }
        return students;
    }

    public static List<String> getAllSubjects() throws SQLException {
        List<String> subjects = new ArrayList<>();
        String sql = "SELECT subject_id, subject_name FROM subjects ORDER BY subject_name";

        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                subjects.add(rs.getString("subject_id") + " - " + rs.getString("subject_name"));
            }
        }
        return subjects;
    }

    public static boolean markAttendance(String studentInfo, String subjectInfo, String date, String status)
            throws SQLException {


        String studentId = studentInfo.split(" - ")[0];
        String subjectId = subjectInfo.split(" - ")[0];

        String sql = "INSERT INTO attendance (student_id, subject_id, date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            stmt.setString(2, subjectId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setString(4, status);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }


    public static boolean isAttendanceMarked(String studentId, String subjectId, String date) throws SQLException {
        String sql = "SELECT 1 FROM attendance WHERE student_id = ? AND subject_id = ? AND date = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId.split(" - ")[0]);
            stmt.setString(2, subjectId.split(" - ")[0]);
            stmt.setDate(3, Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}