//package org.example.lms1.biz.enrollments.repository;
//
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//
//@Mapper
//public interface EnrollmentsMapper {
//
//    @Select("SELECT COUNT(*) FROM enrollments WHERE user_id = #{userId} AND course_id = #{courseId}")
//    boolean isAlreadyEnrolled(@Param("userId") int userId, @Param("courseId") int courseId);
//
//    @Insert("""
//        INSERT INTO enrollments (user_id, course_id)
//        VALUES (#{userId}, #{courseId})
//    """)
//    int enrollCourse(@Param("userId") int userId, @Param("courseId") int courseId);
//}