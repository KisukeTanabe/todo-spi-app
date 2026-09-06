package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/setting")
    public String setting() {
        return "setting";
    }
    
    @PostMapping("/saveUser")
    public String saveUser(
        @RequestParam String username,HttpSession session) {
            // セッションに保存
            session.setAttribute("username", username);
            // DBに保存
            User user = userRepository.findById(1L).orElse(new User());

            user.setUsername(username);

            userRepository.save(user);
        
            return "redirect:/";
        }
}
