package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository	// 컴포넌트 스캔 시 사용
public class MemoryMemberRepository implements MemberRepository {
	
	// 실무에서는 동시성 문제 고려하여 공유되는 변수일 경우  ConcurrentHashMap, AtomicLong 등 사용
	private static Map<Long, Member> store = new HashMap<>();	// id와 회원 매치해주기 위해 Map 사용
	private static long sequence = 0L;	// 키 값을 생성
	

	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(store.get(id));	// null이어도 Optional 가능
	}
	
	@Override
	public Optional<Member> findByName(String name) {
		return store.values().stream()	// 반복~
				.filter(member -> member.getName().equals(name))	// 람다 사용해서 member.getName()이 name과 같은지 확인
				.findAny();	// 하나 찾으면 반환, 없으면 optional에 null 포함해서 반환
	}
	
	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values());	// store에 있는 모든 멤버 반환
	}
	
	public void clearStore() {
		store.clear();
	}

}
