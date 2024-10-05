package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JpaRepository 상속한 인터페이스만 만들어두면 별도의 구현이 필요 없다!!
    @Override
    Optional<Member> findByName(String name);
}
