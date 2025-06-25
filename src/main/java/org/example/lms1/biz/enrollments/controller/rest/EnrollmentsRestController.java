package org.example.lms1.biz.enrollments.controller.rest;


import org.example.lms1.biz.course.service.CourseService;
import org.example.lms1.biz.enrollments.model.EnrollmentRequest;
import org.example.lms1.biz.enrollments.model.dto.EnrollmentsDTO;
import org.example.lms1.biz.enrollments.service.EnrollmentsService;
import org.example.lms1.biz.user.model.dto.UserDTO;
import org.example.lms1.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentsRestController {
    @Autowired
    private CourseService courseService; // ✅ Thêm dòng này

    @Autowired
    private EnrollmentsService enrollmentService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('instructor', 'student')")
    public ResponseEntity<String> registerCourse(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody EnrollmentRequest request
    ) {
        int userId = userDetails.getUserId(); // ✅ lấy user từ token
        boolean success = enrollmentService.enrollUserInCourse(userId, request.getCourseId());
        if (success) {
            return ResponseEntity.ok("Đăng ký thành công");
        } else {
            return ResponseEntity.badRequest().body("Người dùng đã đăng ký khóa học này rồi");
        }
    }
    @GetMapping("/my-courses")
    @PreAuthorize("hasAnyRole('admin', 'instructor', 'student')")
    public ResponseEntity<List<EnrollmentsDTO>> getMyCourses() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = null;

        if (principal instanceof CustomUserDetails customUser) {
            userId = customUser.getUserId();
            System.out.println("🔍 User ID: " + userId);
        }

        List<EnrollmentsDTO> courses = enrollmentService.getEnrolledCourses(userId);
        return ResponseEntity.ok(courses);
    }
    @DeleteMapping("/unenroll")
    @PreAuthorize("hasAnyRole('admin', 'instructor', 'student')")
    public ResponseEntity<String> unenrollCourse(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam int courseId) {
        int userId = userDetails.getUserId();

        int rows = enrollmentService.deleteEnrollment(userId, courseId);
        if (rows > 0) {
            return ResponseEntity.ok("Unenroll successful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enrollment not found");
        }
    }

    @GetMapping("/course/{courseId}/enrollments")
    @PreAuthorize("hasAnyRole('admin', 'instructor')")
    public ResponseEntity<?> getEnrollmentsByCourse(
            @PathVariable int courseId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        // Nếu là instructor, kiểm tra quyền sở hữu khóa học
        if (userDetails.hasRole("instructor")) {
            boolean isOwner = courseService.isInstructorOfCourse(userDetails.getUserId(), courseId);
            if (!isOwner) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền truy cập khóa học này");
            }
        }

        List<UserDTO> enrolledUsers = enrollmentService.getEnrolledUsersByCourse(courseId);
        return ResponseEntity.ok(enrolledUsers);
    }

    @DeleteMapping("/admin/unenroll")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> adminUnenrollUserFromCourse(
            @RequestParam int userId,
            @RequestParam int courseId) {

        int rows = enrollmentService.deleteEnrollment(userId, courseId);
        if (rows > 0) {
            return ResponseEntity.ok("Admin unenroll successful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enrollment not found");
        }
    }

}
