package hello.core.member;

public interface MemberRepository {

    void save(Member member); // 회원을 저장하는 기능

    Member findById(Long memberId); // 회원을 찾는 기능
}
