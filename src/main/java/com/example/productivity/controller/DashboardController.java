package com.example.productivity.controller;

import com.example.productivity.dto.MonthlyStat;
import com.example.productivity.service.TaskService;
import com.example.productivity.service.UserService;
import com.example.productivity.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class DashboardController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long userId = getCurrentUserId();

        int total = taskService.countTotalTasksByUser(userId);
        int completed = taskService.countCompletedTasksByUser(userId);
        int rate = total == 0 ? 0 : (int) Math.round((completed * 100.0) / total);

        List<MonthlyStat> monthly = taskService.countMonthlyTaskStats(userId);

        model.addAttribute("total", total);
        model.addAttribute("completed", completed);
        model.addAttribute("rate", rate);
        model.addAttribute("monthly", monthly);
        return "dashboard";
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        return user.getId();
    }
}
