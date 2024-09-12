package com.cmj.app.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/auth")
@Controller
public class AuthController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("auth/login");
    }

    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("auth/signup");
    }


}
