package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // 같은 객체인 것이 좋음 !!
    MemberService memberService;
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemoryMemberRepository memberRepository;
    // 지금은 static이라 문제 없지만 서비스의 MMR과 테케의 MMR이 서로 다른 레포지토리임...
    // MemberService에서
    // public MemberService(MemberRepository memberRepository) {
    //        this.memberRepository = memberRepository;
    //    }
    // 즉 멤버 레포를 직접 입력하는 것으로 변경 -> 같은 메모리 멤버 레포지토리 사용
    // 의존성 주입(dependency injection)

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("jack");
        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //Given
        Member member1 = new Member();
        member1.setName("mm");

        Member member2 = new Member();
        member2.setName("mm");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}