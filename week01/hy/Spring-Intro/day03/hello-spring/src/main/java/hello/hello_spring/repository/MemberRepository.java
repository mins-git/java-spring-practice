package hello.hello_spring.repository;

import java.util.List;
import java.util.Optional;

import hello.hello_spring.domain.Member;

import javax.swing.text.html.Option;

public interface MemberRepository {
	Member save(Member member);	// 회원 저장
	Optional<Member> findById(Long id);			// id로 회원 찾기
	Optional<Member> findByName(String name);	// 이름으로 찾기
	List<Member> findAll();						// 저장된 모든 회원 리스트 반환
}
