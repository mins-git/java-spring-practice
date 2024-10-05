package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

//@Repository // 리포지토리 추상 클래스의 구현 클래스 역시 스프링이 확인할 수 없다. 어노테이션이 필요하다.
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // Member 변수를 저장할 HashMap
    private static long sequence = 0L; // store HashMap의 key

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member); // key: id, value: merber obj
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 입력 받은 name과 같은 member 객체
                .findAny(); // 하나라도 있으면 해당 객체를 즉시 반환, 없으면 NULL
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
