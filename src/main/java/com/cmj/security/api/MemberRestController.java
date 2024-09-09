package com.cmj.security.api;

import com.cmj.security.domain.entity.Member;
import com.cmj.security.dto.MemberRequest;
import com.cmj.security.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping
    public List<Member> findAll() {
        return memberService.findAll();
    }

    @GetMapping("{username}")
    public Member findByUsername(@PathVariable String username) {
        return memberService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("user.not.found"));
    }
}
