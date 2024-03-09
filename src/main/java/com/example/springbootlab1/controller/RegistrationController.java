package com.example.springbootlab1.controller;

import com.example.springbootlab1.repo.PostRepository;
import com.example.springbootlab1.service.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class RegistrationController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String userRequests(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "user-requests";
    }
}
