package ssafy.ssafy_spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ssafy.ssafy_spring.service.MemberService;

@Controller // 스프링 컨테이너에 이 컨트롤러가 들어감.
public class MemberController {


    private final MemberService memberService;


    @Autowired // spring이 컨테이너에서 가져옴.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

}
