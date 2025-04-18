package studentresult.model;

import java.io.Serializable;

public class Result implements Serializable {
    private String studentId;
    private String semester;
    private String courseId;
    private double marks;

    public Result(String studentId, String semester, String courseId, double marks) {
        this.studentId = studentId;
        this.semester = semester;
        this.courseId = courseId;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseId() {
        return courseId;
    }

    public double getMarks() {
        return marks;
    }

    public double getGPA() {
        if (marks >= 80) return 4.0;
        else if (marks >= 75) return 3.75;
        else if (marks >= 70) return 3.5;
        else if (marks >= 65) return 3.25;
        else if (marks >= 60) return 3.0;
        else if (marks >= 55) return 2.75;
        else if (marks >= 50) return 2.5;
        else if (marks >= 45) return 2.25;
        else if (marks >= 40) return 2.0;
        else return 0.0;
    }
}
