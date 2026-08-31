package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    
    @GetMapping("/setting")
    public String setting() {
        return "setting";
    }
    
    @PostMapping("/saveUser")
    public String saveUser(
        @RequestParam String username,HttpSession session) {
            session.setAttribute("username", username);
        
            return "redirect:/";
        }

}
