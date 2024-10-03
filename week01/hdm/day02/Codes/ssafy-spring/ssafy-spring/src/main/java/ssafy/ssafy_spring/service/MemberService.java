package ssafy.ssafy_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssafy.ssafy_spring.domain.Member;
import ssafy.ssafy_spring.repository.MemberRepository;
import ssafy.ssafy_spring.repository.MemoryMemberRepsotiroy;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;


    @Autowired // Autowired가 너는 memberRepository가 필요하구나 하면서 넣어줌.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member){
        // 같은 이름 회원x 가정 중복x 가정
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }


}
