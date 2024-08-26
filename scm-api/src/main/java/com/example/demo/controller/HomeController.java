package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
 
    @GetMapping(value= {"/","/home","/index"})
    public String homePage(Model model){
        model.addAttribute("name","momo");
        return "home";
    }

    @GetMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin", true);
        return "about";
    }

    @GetMapping("/services")
    public String servicesPage(){
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    
    
}
