package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest     // 스프링 컨테이너와 테스트를 함께 실행
@Transactional      // (테스트 이후 롤백)
public class MemberServiceIntegrationTest {

    // 테스트 케이스는 injection 후 따로 사용하지 않기 때문에 간단하게 Autowired 사용 + MemoryMemberRepository -> MemberRepository
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /*
    @Transactional이 afterEach의 역할
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    */

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");
        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //Given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
