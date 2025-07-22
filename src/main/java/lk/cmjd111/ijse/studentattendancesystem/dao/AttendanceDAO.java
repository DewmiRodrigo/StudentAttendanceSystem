package lk.cmjd111.ijse.studentattendancesystem.dao;

import lk.cmjd111.ijse.studentattendancesystem.db.DBConnection;
import lk.cmjd111.ijse.studentattendancesystem.model.AttendanceRecord;

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

        if (isAttendanceMarked(studentId, subjectId, date)) {
            throw new SQLException("Attendance already marked for this student/subject/date");
        }

        String sql = "INSERT INTO attendance (student_id, subject_id, date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            stmt.setString(2, subjectId);
            stmt.setDate(3, Date.valueOf(date));
            stmt.setString(4, status);

            return stmt.executeUpdate() > 0;
        }
    }


    public static boolean isAttendanceMarked(String studentId, String subjectId, String date) throws SQLException {
        String sql = "SELECT 1 FROM attendance WHERE student_id = ? AND subject_id = ? AND date = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            stmt.setString(2, subjectId);
            stmt.setDate(3, Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }


    public static List<AttendanceRecord> getFilteredAttendance(String studentFilter, String subjectFilter) throws SQLException {
        List<AttendanceRecord> records = new ArrayList<>();

        String sql = "SELECT s.full_name, sub.subject_name, a.date, a.status " +
                "FROM attendance a " +
                "JOIN students s ON a.student_id = s.student_id " +
                "JOIN subjects sub ON a.subject_id = sub.subject_id WHERE 1=1";

        List<String> params = new ArrayList<>();

        if (studentFilter != null && !studentFilter.isEmpty()) {
            sql += " AND s.student_id = ?";
            params.add(studentFilter.split(" - ")[0]);
        }

        if (subjectFilter != null && !subjectFilter.isEmpty()) {
            sql += " AND sub.subject_id = ?";
            params.add(subjectFilter.split(" - ")[0]);
        }

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    records.add(new AttendanceRecord(
                            rs.getString("full_name"),
                            rs.getString("subject_name"),
                            rs.getDate("date"),
                            rs.getString("status")
                    ));
                }
            }
        }

        return records;
    }
}
