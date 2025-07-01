package org.example.lms1.biz.user.service;

import org.example.lms1.biz.user.model.User;
import org.example.lms1.biz.commoncode.email.EmailService;
import org.example.lms1.biz.user.model.dto.UserDTO;
import org.example.lms1.biz.user.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // ✅ Xử lý đăng nhập
    public boolean login(UserDTO userDTO) {
        System.out.println("Trying login with username: " + userDTO.getUsername());
        System.out.println("Raw password: " + userDTO.getPassword());

        User user = userMapper.findByUsername(userDTO.getUsername());

        if (user == null) {
            System.out.println("User not found");
            return false;
        }

        // ❌ Bỏ dòng kiểm tra xác minh
        // if (!user.isVerified()) {
        //     System.out.println("User not verified");
        //     return false;
        // }

        System.out.println("Found user: " + user.getUsername());
        System.out.println("Hashed password from DB: " + user.getPassword());

        boolean match = passwordEncoder.matches(userDTO.getPassword(), user.getPassword());
        System.out.println("Password match: " + match);

        return match;
    }

    // ✅ Xử lý đăng ký
    public boolean register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setRole(User.Role.valueOf(userDTO.getRole()));


        boolean inserted = userMapper.insertUser(user) > 0;

        // Gửi email xác thực nếu cần
        // emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());

        return inserted;
    }
    // ✅ Lấy danh sách người dùng có điều kiện
    public List<User> getUsers(Integer userId, String role, Boolean isVerified, String username) {
        return userMapper.findUsersByConditions(userId, role, isVerified, username);
    }

    public boolean updateUser(Long id, UserDTO userDTO) {
        User existingUser = userMapper.findById(id);
        if (existingUser == null) {
            return false;
        }

        existingUser.setUserId(id.intValue()); // ⚠️ cần thiết để mapper có user_id

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setRole(User.Role.valueOf(userDTO.getRole()));


        if (userDTO.getIsVerified() != null) {
            existingUser.setVerified(userDTO.getIsVerified());
        }


        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return userMapper.updateUser(existingUser) > 0;
    }
    public boolean deleteUser(int id) {
        return userMapper.deleteUserById(id) > 0;
    }

}
