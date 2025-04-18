package studentresult.model;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseId;
    private String courseTitle;
    private String semester;

    public Course(String courseId, String courseTitle, String semester) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.semester = semester;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getSemester() {
        return semester;
    }
}
