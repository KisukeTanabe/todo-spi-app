package com.example.renshu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {
    @GetMapping("/task")
    public String showTask(Model model) {
        Task task = new Task("Javaの課題",false);
        model.addAttribute("task", task);
        return "task";
    }
    
}
