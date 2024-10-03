package ssafy.ssafy_spring.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ssafy.ssafy_spring.repository.MemberRepository;
import ssafy.ssafy_spring.repository.MemoryMemberRepsotiroy;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepsotiroy();
    }


}
