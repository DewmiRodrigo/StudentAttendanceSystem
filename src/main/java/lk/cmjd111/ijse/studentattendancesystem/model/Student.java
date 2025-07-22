package lk.cmjd111.ijse.studentattendancesystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty studentId;
    private final StringProperty fullName;
    private final StringProperty email;
    private final StringProperty courseId;

    public Student(String studentId, String fullName, String email, String courseId) {
        this.studentId = new SimpleStringProperty(studentId);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.courseId = new SimpleStringProperty(courseId);
    }

    public StringProperty studentIdProperty() { return studentId; }
    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty emailProperty() { return email; }
    public StringProperty courseIdProperty() { return courseId; }

    public String getStudentId() { return studentId.get(); }
    public String getFullName() { return fullName.get(); }
    public String getEmail() { return email.get(); }
    public String getCourseId() { return courseId.get(); }

    public void setStudentId(String studentId) { this.studentId.set(studentId); }
    public void setFullName(String fullName) { this.fullName.set(fullName); }
    public void setEmail(String email) { this.email.set(email); }
    public void setCourseId(String courseId) { this.courseId.set(courseId); }
}
