package lk.cmjd111.ijse.studentattendancesystem.model;

import java.sql.Date;

public class AttendanceRecord {
    private String studentName;
    private String courseName;
    private Date date;
    private String status;

    public AttendanceRecord(String studentName, String courseName, Date date, String status) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.date = date;
        this.status = status;
    }


    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }


    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("AttendanceRecord[student=%s, course=%s, date=%s, status=%s]",
                studentName, courseName, date, status);
    }
}