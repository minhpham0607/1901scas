package org.example.lms1.biz.user.controller.rest;

import org.example.lms1.biz.user.model.User;
import org.example.lms1.biz.user.model.dto.UserDTO;
import org.example.lms1.biz.user.service.UserService;
import org.example.lms1.utils.JwtTokenUtil;
import org.example.lms1.biz.user.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // ✅ API đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        boolean success = userService.login(userDTO);

        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Tên đăng nhập hoặc mật khẩu không đúng"
            ));
        }

        User user = userMapper.findByUsername(userDTO.getUsername());
        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(Map.of(
                "message", "Đăng nhập thành công",
                "token", token
        ));
    }

    // ✅ API đăng ký
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        boolean created = userService.register(userDTO);
        return created ?
                ResponseEntity.ok("User registered successfully") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
    }

    // ✅ API lấy danh sách người dùng theo điều kiện
    @GetMapping("/list")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean isVerified,
            @RequestParam(required = false) String username) {

        List<User> users = userService.getUsers(userId, role, isVerified, username);
        return ResponseEntity.ok(users);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO
    ) {
        try {
            boolean updated = userService.updateUser(id, userDTO);
            if (updated) {
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("Xóa người dùng thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
        }
    }
}
