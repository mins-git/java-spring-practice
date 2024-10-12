package hello.core.member;

public class MemberServiceImpl implements MemberService {
    // 인터페이스만 있으면 NullPointerException
    // 구현 객체 선택해야됨
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    // 싱글톤(스프링 컨테이너) 검증 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

//    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
