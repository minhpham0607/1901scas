package org.example.lms1.biz.course.controller.rest;

import org.example.lms1.biz.course.model.Course;
import org.example.lms1.biz.course.model.dto.CourseDTO;
import org.example.lms1.biz.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO) {
        boolean created = courseService.createCourse(courseDTO);
        if (!created) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tạo khóa học thất bại");
        }
        return ResponseEntity.ok("Tạo khóa học thành công");
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/list")
    public ResponseEntity<List<Course>> listCourses(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer instructorId,
            @RequestParam(required = false) String status
    ) {
        List<Course> courses = courseService.getCourses(categoryId, instructorId, status);
        return ResponseEntity.ok(courses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable("id") Integer courseId,
            @RequestBody Course course
    ) {
        course.setCourseId(courseId); // Gán ID từ URL vào object
        boolean success = courseService.updateCourse(course);
        if (success) {
            return ResponseEntity.ok("Course updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found or update failed");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Integer courseId) {
        boolean success = courseService.deleteCourse(courseId);
        if (success) {
            return ResponseEntity.ok("Course deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }

}
