package org.example.lms1.biz.enrollments.model;

import java.sql.Timestamp;

public class Enrollments {
    private int enrollmentId;
    private int userId;
    private int courseId;
    private Timestamp enrolledAt;
    private String status; // 'active', 'completed', 'dropped'

    // Getters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public Timestamp getEnrolledAt() {
        return enrolledAt;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setEnrolledAt(Timestamp enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
