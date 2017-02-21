package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotreloadController {

    @GetMapping("/")
    public String hello(Model model) {
        String greeting = new Dependency().getName();
        System.out.println(greeting + " dependant2  123");
        model.addAttribute("greeting", greeting);
        return "greeting";
    }

    @GetMapping("/2")
    public String hello2(Model model) {
        String greeting = new Dependency().getName();
        System.out.println(greeting);
        model.addAttribute("greeting", greeting);
        return "greeting2";
    }
}
