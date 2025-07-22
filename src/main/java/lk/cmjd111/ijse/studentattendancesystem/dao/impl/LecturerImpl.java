package lk.cmjd111.ijse.studentattendancesystem.dao.impl;

import lk.cmjd111.ijse.studentattendancesystem.dao.LecturerDAO;
import lk.cmjd111.ijse.studentattendancesystem.db.DBConnection;
import lk.cmjd111.ijse.studentattendancesystem.model.Lecturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerImpl implements LecturerDAO {
    private static final LecturerDAO lecturerDAO = new LecturerImpl();

    private LecturerImpl() {}

    public static LecturerDAO getInstance() {
        return lecturerDAO;
    }

    @Override
    public boolean saveLecturer(Lecturer lecturer) throws SQLException {
        String sql = "INSERT INTO lecturers (lec_id, full_name, email, course_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturer.getLecId());
            stmt.setString(2, lecturer.getFullName());
            stmt.setString(3, lecturer.getEmail());
            stmt.setString(4, lecturer.getCourseId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateLecturer(Lecturer lecturer) throws SQLException {
        String sql = "UPDATE lecturers SET full_name = ?, email = ?, course_id = ? WHERE lec_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecturer.getFullName());
            stmt.setString(2, lecturer.getEmail());
            stmt.setString(3, lecturer.getCourseId());
            stmt.setString(4, lecturer.getLecId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteLecturer(String lecId) throws SQLException {
        String sql = "DELETE FROM lecturers WHERE lec_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Lecturer findLecturer(String lecId) throws SQLException {
        String sql = "SELECT * FROM lecturers WHERE lec_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lecId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Lecturer(
                            rs.getString("lec_id"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("course_id")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Lecturer> getAllLecturers() throws SQLException {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lecturers.add(new Lecturer(
                        rs.getString("lec_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("course_id")
                ));
            }
        }
        return lecturers;
    }

    @Override
    public List<String> getAllCourseIds() throws SQLException {
        List<String> courseIds = new ArrayList<>();
        String sql = "SELECT course_id FROM courses";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courseIds.add(rs.getString("course_id"));
            }
        }
        return courseIds;
    }
}