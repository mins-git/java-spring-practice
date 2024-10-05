package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service  // 컴포넌트 스캔 시 사용
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired    // 컴포넌트 스캔 시 사용
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {
        // 같은 이름 회원 X
        validateDuplicateMember(member);
        memberRepository.save(member);  // 회원정보 저장
        return member.getId();          // id 반환
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
        //        result.orElseGet(); // 값이 있으면 꺼내고 없으면 특정 메소드 실행
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 모든 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // Id로 회원조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
