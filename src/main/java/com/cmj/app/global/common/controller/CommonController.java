package com.cmj.app.global.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class CommonController {

    @RequestMapping("/")
    public String index(Authentication authentication) {
        return "main/index";
    }
}
