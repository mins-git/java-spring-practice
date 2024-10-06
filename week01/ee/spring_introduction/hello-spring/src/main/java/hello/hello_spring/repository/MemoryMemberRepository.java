package hello.hello_spring.repository;
import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); //회원 정보를 저장
    private static long sequence = 0L; //회원 id 생성할 때 사용할 sequence 변수

    //회원 정보 저장 메서드
    @Override
    public Member save(Member member) {
        member.setId(++sequence); //id 세팅
        store.put(member.getId(), member); //store에 저장
        return member;
    }

    //id로 회원 정보 찾는 메서드
    @Override
    public Optional<Member> findByID(Long id) {
        return Optional.ofNullable(store.get(id)); //Optional.ofNullable() 반환값이 null일 가능성 처리
    }

    //이름으로 회원 정보 찾는 메서드
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() //Map의 values()로 모든 회원 목록을 stream으로 반환
                .filter(member -> member.getName().equals(name)) //이름이 일치하는 회원 필터링
                .findAny(); //조건에 맞는 회원 찾으면 반환
    }

    //저장된 모든 회원 정보를 리스트로 반환
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //member들 반환
    }

    public void clearStore() {
        store.clear();
    }
}
