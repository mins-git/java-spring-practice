package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscoutPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// AppConfig: 객체의 생성과 연결 담당
// 객체를 생성하고 연결하는 역할(AppConfig)과 실행하는 역할(인터페이스 구현체)이 명확히 분리되었다.
@Configuration
public class AppConfig {
    /*
    리팩토링 전:

    // 구현 객체를 생성하고, 연결
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); //
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                new MemoryMemberRepository(),
                new FixDiscountPolicy());
    }
    */


    // 리팩토링 후 + 스프링 기반으로 변경(어노테이션 추가):
    @Bean
    public MemberService memberService () {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // 할인 정책 변경 시 AppConfig의 코드만 변경하면 된다!!
//        return new FixDiscountPolicy();  // 고정 금액 할인
        return new RateDiscountPolicy();    // 정률 할인
    }

}
