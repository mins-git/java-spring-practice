package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service // 순수한 클래스는 스프링이 확인할 수 없다. 어노테이션을 붙여줘야 한다.
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired // 아하 ! MemberService는 MemberRepository 클래스의 객체가 필요하구나 ! 현재 구현체인 MemoryMemberRepository 인스턴스를 넣어주마.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    * 회원가입
    * */
    public Long join(Member member) {
        // 이름 중복 제거
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /*
    * 전체 회원 조회
    * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}





















