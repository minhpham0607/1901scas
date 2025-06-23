//package org.example.lms1.biz.enrollments.controller.rest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/enrollments")
//public class EnrollmentsRestController {
//
//    @Autowired
//    private EnrollmentService enrollmentService;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerCourse(
//            @RequestParam int userId,
//            @RequestParam int courseId) {
//        boolean success = enrollmentService.enrollUserInCourse(userId, courseId);
//        if (success) {
//            return ResponseEntity.ok("Đăng ký thành công");
//        } else {
//            return ResponseEntity.badRequest().body("Người dùng đã đăng ký khóa học này rồi");
//        }
//    }
//}
