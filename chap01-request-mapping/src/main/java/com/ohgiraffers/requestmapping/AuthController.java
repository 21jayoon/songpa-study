package com.ohgiraffers.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/session")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/session")
    public ModelAndView login(@RequestParam String id, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Simple validation for demonstration
        if ("admin".equals(id) && "password".equals(password)) {
            modelAndView.setViewName("redirect:/success");
            return modelAndView;
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return modelAndView;
        }
    }

    @GetMapping("/success")
    public ModelAndView success() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.addObject("message", "로그인 성공!");
        return modelAndView;
    }
}