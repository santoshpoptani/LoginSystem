package com.example.loginwithjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public String userAccess(){
        return "User Content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String modAccess(){
        return "Moderator Content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "Admin Content";
    }
}
