package org.example.lms1.biz.user.repository;


import org.example.lms1.biz.user.model.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
    @Insert("""
        INSERT INTO users (username, password, email, full_name, role, is_verified, verified_at)
        VALUES (#{username}, #{password}, #{email}, #{fullName}, #{role}, TRUE, NOW())
    """)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);
}
