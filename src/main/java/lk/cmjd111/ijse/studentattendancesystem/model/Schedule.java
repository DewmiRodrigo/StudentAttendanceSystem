package lk.cmjd111.ijse.studentattendancesystem.model;

public class Schedule {
    private String scheduleId;
    private String courseName;
    private String lecturerName;
    private String classDate;
    private String startTime;
    private String endTime;
    private String hallNumber;

    public Schedule(String scheduleId, String courseName, String lecturerName, String classDate,
                    String startTime, String endTime, String hallNumber) {
        this.scheduleId = scheduleId;
        this.courseName = courseName;
        this.lecturerName = lecturerName;
        this.classDate = classDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hallNumber = hallNumber;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(String hallNumber) {
        this.hallNumber = hallNumber;
    }
}
