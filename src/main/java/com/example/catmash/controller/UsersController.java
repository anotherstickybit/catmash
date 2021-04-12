package com.example.catmash.controller;

import com.example.catmash.entity.User;
import com.example.catmash.manager.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {
    private final UserManager userManager;

    @PostMapping("/add/{name}")
    public User register(@PathVariable String name) {
        return userManager.register(name);
    }
}
