package org.example.lms1.biz.user.service;

import org.example.lms1.biz.user.model.User; // ✅ Đúng class User
import org.example.lms1.biz.commoncode.email.EmailService;
import org.example.lms1.biz.user.model.dto.UserDTO;
import org.example.lms1.biz.user.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public boolean login(UserDTO userDTO) {
        System.out.println("Trying login with username: " + userDTO.getUsername());
        System.out.println("Raw password: " + userDTO.getPassword());

        User user = userMapper.findByUsername(userDTO.getUsername());

        if (user == null) {
            System.out.println("User not found");
            return false;
        }

        System.out.println("Found user: " + user.getUsername());
        System.out.println("Hashed password from DB: " + user.getPassword());

        boolean match = passwordEncoder.matches(userDTO.getPassword(), user.getPassword());
        System.out.println("Password match: " + match);

        return match;
    }

    public boolean register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setRole(userDTO.getRole());

        boolean inserted = userMapper.insertUser(user) > 0;

        // Optional: Gửi email xác thực nếu cần
        // emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());

        return inserted;
    }
}
