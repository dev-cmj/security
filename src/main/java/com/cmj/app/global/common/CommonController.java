package com.cmj.app.global.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class CommonController {

    @RequestMapping("/")
    public String index(
            Authentication authentication
    ) {
        log.info("authentication: {}", authentication);

        if (authentication == null) {
            return "redirect:/auth/login";
        }

        return "redirect:/home";
    }

}
