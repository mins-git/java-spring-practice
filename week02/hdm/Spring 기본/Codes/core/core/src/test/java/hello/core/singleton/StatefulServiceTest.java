package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {


    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);


        // ThreadA : A사용자 만원 주문
        statefulService1.order("userA", 10000);
        // ThreadB: B사용자 2만원 주문
        statefulService2.order("userA", 20000);


        // ThreadA: 사용자 A 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        // 만원이 되어야 되는데 2만원임.
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

//    // 정상적으로 돌리면
//    @Test
//    void statefulServiceSingleton() {
//        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
//        StatefulService statefulService1 = ac.getBean(StatefulService.class);
//        StatefulService statefulService2 = ac.getBean(StatefulService.class);
//
//
//        // ThreadA : A사용자 만원 주문
//        int userAPrice = statefulService1.order("userA", 10000);
//        // ThreadB: B사용자 2만원 주문
//        int userBPrice = statefulService2.order("userA", 20000);
//
//
//        // ThreadA: 사용자 A 주문 금액 조회
//        System.out.println("price = " + price);
//
//        // 만원이 되어야 되는데 2만원임.
//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
//    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}