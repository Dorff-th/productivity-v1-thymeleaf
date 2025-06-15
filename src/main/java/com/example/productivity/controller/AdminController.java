package com.example.productivity.controller;

import com.example.productivity.dto.UserTaskStatDTO;
import com.example.productivity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        List<UserTaskStatDTO> stats = userService.getUserTaskStats();
        model.addAttribute("stats", stats);
        return "admin-dashboard";
    }
}
