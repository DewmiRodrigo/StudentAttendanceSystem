package lk.cmjd111.ijse.studentattendancesystem.dao.impl;

import lk.cmjd111.ijse.studentattendancesystem.dao.StudentDAO;
import lk.cmjd111.ijse.studentattendancesystem.db.DBConnection;
import lk.cmjd111.ijse.studentattendancesystem.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentImpl implements StudentDAO {
    private static final StudentDAO studentDAO = new StudentImpl();

    private StudentImpl() {}

    public static StudentDAO getInstance() {
        return studentDAO;
    }

    @Override
    public boolean saveStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (student_id, full_name, email, course_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getStudentId());
            stmt.setString(2, student.getFullName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getCourseId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET full_name = ?, email = ?, course_id = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getFullName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getCourseId());
            stmt.setString(4, student.getStudentId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteStudent(String studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Student findStudent(String studentId) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getString("student_id"),
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
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("course_id")
                ));
            }
        }
        return students;
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
