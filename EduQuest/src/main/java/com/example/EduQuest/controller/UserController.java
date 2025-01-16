package com.example.EduQuest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user/test")
    public String userEndpoint(Authentication authentication) {
        System.out.println("Authorities: " + authentication.getAuthorities());
        return "Accessible uniquement aux utilisateurs avec le rôle USER.";
    }

    @GetMapping("/admin/test")
    public String adminEndpoint(Authentication authentication) {
        System.out.println("Authorities: " + authentication.getAuthorities());
        return "Accessible uniquement aux utilisateurs avec le rôle ADMIN.";
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String publicEndpoint() {
        return "Accessible à tous.";
    }
}
