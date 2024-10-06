package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원가입 */
    public Long join(Member member) {

        //같은 이름이 있는 중복 회원 없는지 체크
        validateDuplicateMember(member);

        memberRepository.save(member); //회원 정보 저장
        return member.getId(); //저장된 회원의 id 반환
    }

    //중복 회원 체크하는 메서드
    private void validateDuplicateMember(Member member) {
        //memberRepository에서 같은 이름 가진 회원 조회
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* id로 회원 정보 조회 */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findByID(memberId);
    }

}
