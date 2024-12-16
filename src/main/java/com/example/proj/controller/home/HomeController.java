package com.example.proj.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @ResponseBody
    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("login")
    public String loginPage(){
        return "login";
    }
}
