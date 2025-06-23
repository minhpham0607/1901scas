//package org.example.lms1.biz.enrollments.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EnrollmentsService {
//
//    @Autowired
//    private EnrollmentMapper enrollmentMapper;
//
//    public boolean enrollUserInCourse(int userId, int courseId) {
//        if (enrollmentMapper.isAlreadyEnrolled(userId, courseId)) {
//            return false;
//        }
//        enrollmentMapper.enrollCourse(userId, courseId);
//        return true;
//    }
//}
