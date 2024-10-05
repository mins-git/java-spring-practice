# AOP (Aspect Oriented Programming)

- AOP: Aspect Oriented Programming
    - 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리하는것임.

![AOP](img/image.png)

- 예시
```java
@Aspect
@Component
public class TimeTraceAOP {

    @Around("execution(* ssafy.ssafy_spring..*(..))")
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

### @Aspect의 주요 기능:
- 횡단 관심사의 분리: AOP를 사용하면 비즈니스 로직과 상관없는 반복적인 코드를 분리할 수 있다. 이를 통해 코드가 더 깔끔하고 유지보수하기 쉬워짐.

### 성능측정 예시
```java
@Aspect
@Component
public class TimeTraceAOP {

    // ssafy 패키지 하위 모든 메소드에 대해 AOP 적용
    @Around("execution(* ssafy.ssafy_spring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();  // 실제 메소드 실행
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
```