package lk.cmjd111.ijse.studentattendancesystem.dao;

import lk.cmjd111.ijse.studentattendancesystem.model.Lecturer;
import java.sql.SQLException;
import java.util.List;

public interface LecturerDAO {
    boolean saveLecturer(Lecturer lecturer) throws SQLException;
    boolean updateLecturer(Lecturer lecturer) throws SQLException;
    boolean deleteLecturer(String lecId) throws SQLException;
    Lecturer findLecturer(String lecId) throws SQLException;
    List<Lecturer> getAllLecturers() throws SQLException;
    List<String> getAllCourseIds() throws SQLException;
}