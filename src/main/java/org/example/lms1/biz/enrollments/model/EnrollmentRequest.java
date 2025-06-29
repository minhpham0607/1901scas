package org.example.lms1.biz.enrollments.model;


public class EnrollmentRequest {
    private int userId;
    private int courseId;

    public EnrollmentRequest() {}

    public EnrollmentRequest(int userId, int courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}