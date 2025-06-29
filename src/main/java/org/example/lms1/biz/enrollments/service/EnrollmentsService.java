package org.example.lms1.biz.enrollments.service;

import org.example.lms1.biz.enrollments.model.dto.EnrollmentsDTO;
import org.example.lms1.biz.enrollments.repository.EnrollmentsMapper;
import org.example.lms1.biz.user.model.User;
import org.example.lms1.biz.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EnrollmentsService {
    @Autowired
    private EnrollmentsMapper enrollmentMapper;

    public boolean enrollUserInCourse(int userId, int courseId) {
        int count = enrollmentMapper.countEnrollment(userId, courseId);
        if (count > 0) {
            return false;
        }
        enrollmentMapper.enrollCourse(userId, courseId);
        return true;
    }

    public List<EnrollmentsDTO> getEnrolledCourses(int userId) {
        return enrollmentMapper.getEnrolledCoursesByUserId(userId);
    }

    public int deleteEnrollment(int userId, int courseId) {
        return enrollmentMapper.deleteEnrollment(userId, courseId);
    }

    public List<UserDTO> getEnrolledUsersByCourse(int courseId) {
        return enrollmentMapper.getUsersByCourseId(courseId);
    }
    public boolean isStudentEnrolled(int userId, int courseId) {
        return enrollmentMapper.countEnrollment(userId, courseId) > 0;
    }


}