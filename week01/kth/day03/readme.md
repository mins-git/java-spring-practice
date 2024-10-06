# Spring(3) - 7 ~ 9강

# `7강`

# h2 database

- 설치했다.
- 조작했다.

# 순수 JDBC

- 옛 선조들의 방식을 잘 감상했다.

# 스프링 통합 테스트

```java
@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    // 테스트 케이스는 injection 후 따로 사용하지 않기 때문에 간단하게 Autowired 사용
    // MemoryMemberRepository 대신 MemberRepository 사용 (구현체 변경 가능성 고려)
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 여기에 테스트 메소드 추가
    // @Test 어노테이션을 사용하여 각 테스트 케이스를 정의
}
```

- **`@SpringBootTest`:** 스프링 부트 애플리케이션 컨텍스트를 로드하여 통합 테스트를 수행.
- **`@Transactional`:** 각 테스트 메소드를 트랜잭션으로 감싸서 테스트 종료 후 데이터베이스를 롤백. 이를 통해 테스트 간 데이터 독립성을 보장.
- **`@Autowired`:** 스프링의 의존성 주입을 자동으로 수행. 테스트에서 필요한 빈을 자동으로 주입받을 수 있게 해준다.
- **`@Test`:** JUnit에서 테스트 메소드임을 나타냄. 이 어노테이션이 붙은 메소드는 테스트 러너에 의해 실행.

# JDBC Template

**스프링 JdbcTemplate**

- 순수 Jdbc와 같은 설정으로 사용 가능
- JDBC API의 반복 코드를 대부분 제거해주지만, SQL은 직접 작성해야 함

**JdbcTemplate 회원 리포지토리 구현**

```
public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        // 구현 내용
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 구현 내용
    }

    // 기타 메서드 구현...
}
```

**JdbcTemplate 사용을 위한 스프링 설정**

```
@Configuration
public class SpringConfig {
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
```

# JPA

- JPA의 장점
    - 반복 코드와 기본 SQL 자동 생성
    - 객체 중심 설계로 전환
    - 생산성 향상

**JPA 설정 (build.gradle)**

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
    // 기타 의존성...
}
```

**JPA 설정 (application.properties)**

```
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=none
```

**JPA 엔티티 매핑 예시**

```
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // getter, setter...
}
```

**JPA 리포지토리 구현**

```
public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    // 메서드 구현...
}
```

**트랜잭션 설정**

```
@Transactional
public class MemberService {}
```

# 스프링 데이터 JPA

- 구현 클래스 없이 인터페이스만으로 개발 가능
- 기본 CRUD 기능 자동 제공
- 실무에서 필수적으로 사용되는 기술

**스프링 데이터 JPA 리포지토리 인터페이스**

```
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    Optional<Member> findByName(String name);
}
```

**스프링 데이터 JPA 설정**

```
@Configuration
public class SpringConfig {
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
}
```

- 스프링 데이터 JPA의 주요 기능
    - 인터페이스를 통한 기본 CRUD
    - 메서드 이름으로 쿼리 생성
    - 페이징 기능 자동 제공

# `8강`

# **AOP가 필요한 상황**

AOP(Aspect-Oriented Programming)는 다음과 같은 상황에서 유용

- 모든 메소드의 실행 시간을 측정해야 할 때
- 공통 관심사(cross-cutting concern)와 핵심 관심사(core concern)를 분리해야 할 때
- 회원 가입이나 조회 시간 등 특정 기능의 수행 시간을 측정해야 할 때

### **MemberService에 시간 측정 로직 추가 시 문제점**

단순히 try-finally 구문과 `System.currentTimeMillis()` 메서드를 사용하여 시간을 측정하면 다음과 같은 문제가 발생

- 시간 측정 기능이 핵심 비즈니스 로직이 아님에도 불구하고 섞이게 된다.
- 시간 측정은 공통 관심사이지만, 이를 분리하기 어렵다.
- 핵심 비즈니스 로직과 시간 측정 로직이 혼재되어 유지보수가 어려워진다.
- 공통 로직으로 분리하기가 매우 어렵다.
- 시간 측정 로직 변경 시 관련된 모든 메서드를 수정해야 한다.

# **AOP 적용**

AOP(Aspect-Oriented Programming)를 사용하면 이러한 문제를 해결할 수 있다:

- 공통 관심사와 핵심 관심사를 효과적으로 분리할 수 있다.

### **시간 측정을 위한 AOP 구현**

```java
package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hello_spring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
```

### **AOP 적용의 이점**

- 핵심 비즈니스 로직과 공통 관심사를 깔끔하게 분리할 수 있다.
- 시간 측정 로직을 하나의 공통 모듈로 관리할 수 있다.
- 핵심 비즈니스 로직의 가독성과 유지보수성이 향상된다.
- 변경이 필요한 경우 AOP 로직만 수정하면 된다.
- 원하는 대상에만 선택적으로 AOP를 적용할 수 있다.

## **스프링의 AOP 동작 방식**

### **AOP 적용 전 의존관계**

- memberController가 직접 memberService를 의존하는 단순한 구조이다.

### **AOP 적용 후 의존관계**


- 스프링은 memberService의 프록시(가짜 객체)를 생성한다.
- 스프링 빈으로 등록될 때 이 프록시 객체가 등록된다.
- joinPoint.proceed() 호출 시 실제 memberService가 실행된다.
- 결과적으로 helloController는 프록시를 통해 AOP 기능을 사용하게 된다.

### **AOP 적용 전 전체 구조**



### **AOP 적용 후 전체 구조**



실제로 Proxy가 주입되는지 확인하려면 다음과 같이 할 수 있다:

- MemberController 생성자에 다음 코드를 추가한다:
`System.out.println("memberService: " + memberService.getClass());`

# `9강`

## **스프링**

- 스프링 핵심기술
    - dependency, injection, AOP, …
- 스프링 웹 MVC
    - Servlet 기반 기술
    - filter, interceptor, 예외처리, ...
- 스프링 DB 접근 기술
    - transaction, 예외처리, DB 커넥션, ...
- 스프링 부트
    - 모니터링, 프로파일, ...

## **스프링 부트 + JPA**

- 자바 ORM 표준 JPA 프로그래밍
- 스프링부트와 JPA 활용하기
    - 웹 애플리케이션 개발
    - API 개발과 성능 최적화
- 스프링 데이터 JPA
- Querydsl