package hello.core.order;

import hello.core.discount.DiscountPolicy;
//import hello.core.discount.FixDiscoutPolicy;
//import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
//import hello.core.member.MemberService;
//import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {



    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscoutPolicy();       // 고정금액 할인
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();     // 일정 비율 할인
//    DIP 지키려면 어떻게 해야되는가? -> 인터페이스에만 의존하도록 코드 변경
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
