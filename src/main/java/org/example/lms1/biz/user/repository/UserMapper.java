package org.example.lms1.biz.user.repository;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.example.lms1.biz.user.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    // 🔍 Tìm người dùng theo username
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    // 🔍 Tìm người dùng theo ID
    @Select("SELECT * FROM users WHERE user_id = #{id}")
    User findById(@Param("id") Long id);

    // ➕ Thêm người dùng mới
    @Insert("""
        INSERT INTO users (username, password, email, full_name, role, is_verified, verified_at)
        VALUES (#{username}, #{password}, #{email}, #{fullName}, #{role}, TRUE, NOW())
    """)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    // 📋 Lấy danh sách người dùng có điều kiện (role, isVerified, username)
    @Select("""
    <script>
        SELECT 
            user_id AS userId,
            username,
            password,
            email,
            full_name AS fullName,
            role,
            verification_token AS verificationToken,
            is_verified AS isVerified,
            verified_at AS verifiedAt,
            created_at AS createdAt,
            updated_at AS updatedAt
        FROM users
        <where>
            <if test="userId != null">
                AND user_id = #{userId}
            </if>
            <if test="role != null and role != ''">
                AND role = #{role}
            </if>
            <if test="isVerified != null">
                AND is_verified = #{isVerified}
            </if>
            <if test="username != null and username != ''">
                AND username LIKE CONCAT('%', #{username}, '%')
            </if>
        </where>
    </script>
""")
    @Lang(XMLLanguageDriver.class)
    List<User> findUsersByConditions(@Param("userId") Integer userId,
                                     @Param("role") String role,
                                     @Param("isVerified") Boolean isVerified,
                                     @Param("username") String username);

    // 🔄 Cập nhật người dùng
    @Update("""
        UPDATE users SET
            username = #{username},
            password = #{password},
            email = #{email},
            full_name = #{fullName},
            role = #{role},
            is_verified = #{isVerified}
        WHERE user_id = #{userId}
    """)
    int updateUser(User user);
    @Delete("DELETE FROM users WHERE user_id = #{id}")
    int deleteUserById(@Param("id") int id);

}

