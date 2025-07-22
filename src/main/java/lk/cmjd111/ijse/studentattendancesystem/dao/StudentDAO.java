package lk.cmjd111.ijse.studentattendancesystem.dao;

import lk.cmjd111.ijse.studentattendancesystem.model.Student;
import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    boolean saveStudent(Student student) throws SQLException;
    boolean updateStudent(Student student) throws SQLException;
    boolean deleteStudent(String studentId) throws SQLException;
    Student findStudent(String studentId) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    List<String> getAllCourseIds() throws SQLException;
}
