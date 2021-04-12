package com.example.catmash.controller;

import com.example.catmash.entity.Cat;
import com.example.catmash.manager.CatManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/cats")
public class CatController {
    private final CatManager catManager;

    @GetMapping
    public List<Cat> getAll() {
        return catManager.getAll();
    }

    @GetMapping("/pair/{id}")
    public List<Cat> pair(@PathVariable long id) {
        return catManager.pair(id);
    }

    @PostMapping("/vote/{id}")
    public void vote(@PathVariable long id) {
        catManager.vote(id);
    }
}
