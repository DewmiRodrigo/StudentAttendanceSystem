package lk.cmjd111.ijse.studentattendancesystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lecturer {
    private final StringProperty lecId;
    private final StringProperty fullName;
    private final StringProperty email;
    private final StringProperty courseId;

    public Lecturer(String lecId, String fullName, String email, String courseId) {
        this.lecId = new SimpleStringProperty(lecId);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.courseId = new SimpleStringProperty(courseId);
    }


    public StringProperty lecIdProperty() {
        return lecId;
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty courseIdProperty() {
        return courseId;
    }


    public String getLecId() {
        return lecId.get();
    }

    public String getFullName() {
        return fullName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getCourseId() {
        return courseId.get();
    }


    public void setLecId(String lecId) {
        this.lecId.set(lecId);
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setCourseId(String courseId) {
        this.courseId.set(courseId);
    }
}