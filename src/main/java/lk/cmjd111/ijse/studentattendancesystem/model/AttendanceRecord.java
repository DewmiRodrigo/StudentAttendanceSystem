package lk.cmjd111.ijse.studentattendancesystem.model;

import java.sql.Date;

public class AttendanceRecord {
    private String studentName;
    private String subjectName;
    private Date date;
    private String status;

    public AttendanceRecord(String studentName, String subjectName, Date date, String status) {
        this.studentName = studentName;
        this.subjectName = subjectName;
        this.date = date;
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
