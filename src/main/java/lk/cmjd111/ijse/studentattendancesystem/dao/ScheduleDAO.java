package lk.cmjd111.ijse.studentattendancesystem.dao;

import lk.cmjd111.ijse.studentattendancesystem.model.Schedule;
import java.sql.SQLException;
import java.util.List;

public interface ScheduleDAO {
    boolean saveSchedule(Schedule schedule) throws SQLException;
    boolean updateSchedule(Schedule schedule) throws SQLException;
    boolean deleteSchedule(String scheduleId) throws SQLException;
    Schedule findSchedule(String scheduleId) throws SQLException;
    List<Schedule> getAllSchedules() throws SQLException;
    List<String> getAllCourseNames() throws SQLException;
    List<String> getAllLecturerNames() throws SQLException;
}
