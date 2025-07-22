package lk.cmjd111.ijse.studentattendancesystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private final StringProperty courseId;
    private final StringProperty courseName;
    private final StringProperty description;
    private final StringProperty duration;

    public Course(String courseId, String courseName, String description, String duration) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseName = new SimpleStringProperty(courseName);
        this.description = new SimpleStringProperty(description);
        this.duration = new SimpleStringProperty(duration);
    }

    public StringProperty courseIdProperty() {
        return courseId;
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public String getCourseId() {
        return courseId.get();
    }

    public String getCourseName() {
        return courseName.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getDuration() {
        return duration.get();
    }

    public void setCourseId(String courseId) {
        this.courseId.set(courseId);
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }
}