package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;
    // 스프링 컨테이너에 한번 등록 후 계속 그거 쓰면 된다

    // 스프링 빈에 MemberController 등록
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService: " + memberService.getClass());
    }


    @GetMapping("/members/new")     // url: localhost:8080/members/new(@GetMapping: 요청이 GET일 때!)
    public String createForm() {
        return "members/createMemberForm";  // templates/members/createMemberForm.html 렌더링
    }

    @PostMapping("/members/new")    // url: localhost:8080/members/new(@PostMapping: 요청이 POST일 때!)
    public String create(MemberForm form) {     // html의 input으로 받은 내용이 MemberForm의 name으로 입력됨
        Member member = new Member();   // 멤버 객체 생성
        member.setName(form.getName()); // form에서 받은 이름을 member의 name 필드에 설정

        memberService.join(member);     // 회원가입처리: memberService로 전달(회원가입 로직, 회원 리포지토리에 저장)

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();     // 회원 조회한 결과 -> members라는 List에 저장
        model.addAttribute("members", members);     // members 리스트를 model 객체에 담아서 members라는 이름으로 view에 전달
        return "members/memberList";                            // members/memberList.html 렌더링
    }
}
