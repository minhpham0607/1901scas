package org.example.lms1.biz.course.repository;

import org.apache.ibatis.annotations.*;
import org.example.lms1.biz.course.model.Course;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Insert("""
        INSERT INTO courses (title, description, category_id, instructor_id, status, price)
        VALUES (#{title}, #{description}, #{categoryId}, #{instructorId}, #{status}, #{price})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "courseId")
    int insertCourse(Course course);
    @Select("""
    SELECT 
        c.course_id AS courseId,
        c.title,
        c.description,
        c.category_id AS categoryId,
        c.instructor_id AS instructorId,
        u.full_name AS instructorName,
        c.status,
        c.price
    FROM courses c
    LEFT JOIN users u ON c.instructor_id = u.user_id
    WHERE (#{categoryId} IS NULL OR c.category_id = #{categoryId})
      AND (#{instructorId} IS NULL OR c.instructor_id = #{instructorId})
      AND (#{status} IS NULL OR c.status = #{status})
""")
    List<Course> findCourses(
            @Param("categoryId") Integer categoryId,
            @Param("instructorId") Integer instructorId,
            @Param("status") String status
    );

    @Update("""
    UPDATE courses
    SET 
        title = #{title},
        description = #{description},
        category_id = #{categoryId},
        instructor_id = #{instructorId},
        status = #{status},
        price = #{price},
        updated_at = CURRENT_TIMESTAMP
    WHERE course_id = #{courseId}
""")
    int updateCourse(Course course);
    @Delete("DELETE FROM courses WHERE course_id = #{courseId}")
    int deleteCourse(@Param("courseId") Integer courseId);

}
