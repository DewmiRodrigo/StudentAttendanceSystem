package lk.cmjd111.ijse.studentattendancesystem.dao;

import lk.cmjd111.ijse.studentattendancesystem.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDAO {
    boolean addCourse(Course course) throws SQLException;
    boolean updateCourse(Course course) throws SQLException;
    boolean deleteCourse(String courseId) throws SQLException;
    Course searchCourse(String courseId) throws SQLException;
    List<Course> getAllCourses() throws SQLException;
}