package lk.cmjd111.ijse.studentattendancesystem.dao.impl;

import lk.cmjd111.ijse.studentattendancesystem.dao.CourseDAO;
import lk.cmjd111.ijse.studentattendancesystem.db.DBConnection;
import lk.cmjd111.ijse.studentattendancesystem.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseImpl implements CourseDAO {
    private static final CourseDAO courseDAO = new CourseImpl();

    private CourseImpl() {}

    public static CourseDAO getInstance() {
        return courseDAO;
    }

    @Override
    public boolean addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (course_id, course_name, description, duration) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getDescription());
            stmt.setString(4, course.getDuration());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateCourse(Course course) throws SQLException {
        String sql = "UPDATE courses SET course_name = ?, description = ?, duration = ? WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getDescription());
            stmt.setString(3, course.getDuration());
            stmt.setString(4, course.getCourseId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCourse(String courseId) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Course searchCourse(String courseId) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getString("course_id"),
                            rs.getString("course_name"),
                            rs.getString("description"),
                            rs.getString("duration")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("course_id"),
                        rs.getString("course_name"),
                        rs.getString("description"),
                        rs.getString("duration")
                ));
            }
        }
        return courses;
    }
}