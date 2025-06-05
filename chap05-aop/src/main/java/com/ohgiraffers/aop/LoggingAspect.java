package com.ohgiraffers.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    //5. Write LogginsAspect

    /* @Pointcut: Join point 중에서 Advice가 적용될 가능성이 있는 부분을 선별한 것을 말한다.
                = Several join-point를 매치하기 위해 지정한 표현식
       execution([수식어] returntype [className].name(parameter)
       수식어 생략 가능

       -> pointcut 표현식
       *Service.*(..) : 매개변수 0개 이상인 모든 메소드
       *Service.*(*) : 매개변수 1개 이상인 모든 메소드
       *Service.*(*, ..) : 매개변수 2개 이상인 모든 메소드 */

    @Pointcut("execution(* com.ohgiraffers.aop.*Service.*(..))")
    public void logPointcut() {}    // pointcut은 반드시 VOID형으로 선언해줘야 한다.


    /* Join point : Advice가 적용될 수 있는 위치, pointcut으로 패치한 실행 지점.
    이렇게 매치된 조인포인트에서 해야 할 일이 advice다.
    매개변수로 전달한 JoinPoint 객체의 현재 조인 포인트 메소드명, 인수값 등의
    자세한 정보를 액세스 할 수 있다. */
    @Before("LoggingAspect.logPointcut()")
    public void logBefore(JoinPoint joinPoint){
        System.out.println( joinPoint.getTarget());
        System.out.println(joinPoint.getSignature());

        if(joinPoint.getArgs().length > 0){
            System.out.println(joinPoint.getArgs()[0]);
        }
    }

    /*@After advice can make class name omitted(생략 가능)
    when the point-cut is used in the same class.
    But, if the package(directory) is different,
    you have to write the whole class name including package name*/
    @After("logPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("After joinPoint.getTarget() " + joinPoint.getTarget());
        System.out.println("After joinPoint.getSignature() " + joinPoint.getSignature());
        if(joinPoint.getArgs().length > 0){
            System.out.println("After joinPoint.getArgs()[0] " + joinPoint.getArgs()[0]);
        }
    }

    /*returning 속성은 리턴값으로 받아올 object의 매개변수 이름과 동일해야 한다.
    * 또한 joinPoint는 반드시 첫 번째 매개변수로 선언해야 한다.
    * 예. log에 성공한 사람만 분리하고 싶을 때 사용 가능*/
    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result){
        System.out.println("After Returing result="+result);
    }

    /*The name of parameter and the name of throwing 속성 must be the same*/
    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception")
    public void logAfterThrowing(Throwable exception){
        System.out.println("After Throwing exception: " +exception);
    }
    /*Application에서의 출력 결과
    * 3
    * selectMember 메소드 실행
    * After Throwing exception: java.lang.RuntimeException: 해당 ID를 갖고 있는 회원이 없습니다*/

    /*Around Advice is the most powerful advice.
    * This Advice fully controlling the join-point,
    * so the previous all advice that just saw
    * could be combine with the Around advice.
    * 심지어 원본 조인포인트를 언제 실행할지, 실행 자체를 안 할지, 계속 실행할지 여부까지도 제어한다.
    * join-point 매개변수는 ProceedingJoinPoint로 고정되어 있다.
    * JoinPoint의 하위 인터페이스로 원본 조인포인트의 진행 시점을 제어할 수 있다.
    *
    * (Around Addvice는) 언제 실행할지 명확한 어드바이스를 실행할 때 사용하는 것이 좋다.*/
    @Around("logPointcut()")
    public Object logArounc(ProceedingJoinPoint joinPoint) throws  Throwable {
        System.out.println("Around Before : " + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed(); // 원본 조인포인트를 (result라는 객체에 담아) 실행한다.
        System.out.println("Around After: "+joinPoint.getSignature().getName());
        /*원본 조인포인틀르 호출한 쪽 혹은 다른 어드바이스가 다시 실행할 수 있도록 반환한다.*/
        return result;
    }
}
