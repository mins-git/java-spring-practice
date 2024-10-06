package ssafy.ssafy_spring.AOP;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


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


//@Aspect
//@Component
//public class TimeTraceAOP {
//    public class TimeTraceAop {
//        @Around("execution(* ssafy.ssafy_spring..*(..))")
//        public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
//            long start = System.currentTimeMillis();
//            System.out.println("START: " + joinPoint.toString());
//            try {
//                return joinPoint.proceed();
//            } finally {
//                long finish = System.currentTimeMillis();
//                long timeMs = finish - start;
//                System.out.println("END: " + joinPoint.toString() + " " + timeMs +
//                        "ms");
//            }
//        }
//    }
//}