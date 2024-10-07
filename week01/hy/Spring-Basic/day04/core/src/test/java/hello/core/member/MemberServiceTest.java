package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService = new MemberServiceImpl();

    @Test
    void join() {
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);   // 회원 생성

        // when
        memberService.join(member);                                         // 회원가입
        Member findMember = memberService.findMember(1L);           // 회원 조회

        // then
        Assertions.assertThat(member).isEqualTo(findMember);                // 생성한 회원과 조회한 회원이 같은지?
    }
}
