package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;
    // 스프링 컨테이너에 한번 등록 후 계속 그거 쓰면 된다

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
