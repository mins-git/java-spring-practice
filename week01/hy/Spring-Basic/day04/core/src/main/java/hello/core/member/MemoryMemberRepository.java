package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

    // 저장소니까 map

    private static Map<Long, Member> store = new HashMap<>();
    // 원래는 동시성 이슈때매 concurrenthashmap 사용해야돼~ 예제니까 넘어가

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
