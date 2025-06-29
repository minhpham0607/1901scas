package org.example.lms1.biz.course.controller.rest;

import org.example.lms1.biz.course.model.Course;
import org.example.lms1.biz.course.model.dto.CourseDTO;
import org.example.lms1.biz.course.service.CourseService;
import org.example.lms1.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO) {
        boolean created = courseService.createCourse(courseDTO);
        if (!created) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tạo khóa học thất bại");
        }
        return ResponseEntity.ok("Tạo khóa học thành công");
    }
    @GetMapping("/list")
    @PreAuthorize("hasRole('admin') or hasRole('instructor')")
    public ResponseEntity<List<Course>> listCourses(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String status
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer instructorId = null;

        if (principal instanceof CustomUserDetails customUser) {
            boolean isInstructor = customUser.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_instructor"));
            if (isInstructor) {
                instructorId = customUser.getId(); // ✅ lấy đúng userId
            }
            System.out.println("🔍 User ID: " + customUser.getId());
        }

        System.out.printf("getCourses with: categoryId=%s, instructorId=%s, status=%s%n",
                categoryId, instructorId, status);

        List<Course> courses = courseService.getCourses(categoryId, instructorId, status);
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateCourse(@PathVariable("id") Integer courseId, @RequestBody Course course) {
        course.setCourseId(courseId);
        boolean updated = courseService.updateCourse(course);
        if (updated) {
            return ResponseEntity.ok("Course updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Integer courseId) {
        boolean deleted = courseService.deleteCourse(courseId);
        if (deleted) {
            return ResponseEntity.ok("Course deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }

}
