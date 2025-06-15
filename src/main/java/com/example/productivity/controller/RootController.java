package com.example.productivity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToProperPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증 객체가 있고, 익명 사용자가 아닐 때
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/tasks";
        } else {
            return "redirect:/login";
        }
    }
}
