package ssafy.ssafy_spring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ssafy.ssafy_spring.domain.Member;
import ssafy.ssafy_spring.repository.MemberRepository;
import ssafy.ssafy_spring.repository.MemoryMemberRepsotiroy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {


    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    // 테스트명은 과감하게 한국어로 적어도 됨.
    // 테스트는 정상 테스트도 중요하지만 예외테스트가 더 중요함.
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 오른쪽 코드를 실행하는데, IllegalStateException이 터져야해. 를 작성해준것임.

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }
}
