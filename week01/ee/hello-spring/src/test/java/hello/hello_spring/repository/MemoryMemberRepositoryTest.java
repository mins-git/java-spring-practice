package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    //save()기능 테스트
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member); // member 저장

        Member result = repository.findByID(member.getId()).get(); //저장된 회원의 id로 다시 조회해서 회원 정보 가져오기

        // 저장된 회원과 조회한 회원이 동일한지 확인
        //System.out.println("result = "+(result == member));
        Assertions.assertEquals(member, result);
        assertThat(member).isEqualTo(result);
        assertThat(member).isEqualTo(null); //테스트 실패

    }

    @Test
    public void findByname() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); // shift+F6: RENAME
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}

