package org.example.lms1.biz.user.controller;



import org.example.lms1.biz.user.model.dto.UserDTO;
import org.example.lms1.biz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "login"; // templates/login.html
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        boolean success = userService.login(userDTO);
        if (success) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
