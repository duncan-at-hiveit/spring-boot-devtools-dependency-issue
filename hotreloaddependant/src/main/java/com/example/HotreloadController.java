package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotreloadController {

    @Autowired
    private Dependency dependency;

    @GetMapping
    public String hello(Model model) {
        String greeting = dependency.getName();
        System.out.println(greeting + " dependant2  123");
        model.addAttribute("greeting", greeting);
        return "greeting";
    }
}
