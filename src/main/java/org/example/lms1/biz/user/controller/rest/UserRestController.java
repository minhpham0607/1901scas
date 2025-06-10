package org.example.lms1.biz.user.controller.rest;

import org.example.lms1.biz.user.model.User;
import org.example.lms1.biz.user.model.dto.UserDTO;
import org.example.lms1.biz.user.service.UserService;
import org.example.lms1.utils.JwtTokenUtil;
import org.example.lms1.biz.user.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // API đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        boolean success = userService.login(userDTO);

        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Tên đăng nhập hoặc mật khẩu không đúng"
            ));
        }

        // Lấy user từ DB
        User user = userMapper.findByUsername(userDTO.getUsername());

        // Tạo JWT token
        String token = jwtTokenUtil.generateToken(user);

        // Trả về token và thông báo thành công
        return ResponseEntity.ok(Map.of(
                "message", "Đăng nhập thành công",
                "token", token
        ));
    }

    // API đăng ký
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        boolean created = userService.register(userDTO);
        return created ?
                ResponseEntity.ok("User registered successfully") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
    }
}
