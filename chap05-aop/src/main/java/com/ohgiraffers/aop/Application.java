package com.ohgiraffers.aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        //면접 빈출 :
        // AOP가 무엇인지, 무엇의 약자인지, 어떤 원리로 돌아가는지, ADVICE 5가지는 무엇인지, ADVICE 5가지는 어떻게 동작하는지 등

        //AOP는 관점 지향 프로그래밍(Aspect Oriented Programming)의 약자이다.
        //중복되는 공통 코드를 분리, 코드 실행 전이나 후의 시점에
        // 해당 코드를 삽입함으로써 소스 코드의 중복을 줄이고,
        // 필요할 때마다 가져다 쓸 수 있게 객체화하는 기술이다.
        /* Spring의 삼각형
                /\
            DI /  \  AOP
        *     / IoC\
             --------
                PSA   */
        /* AOP 핵심용어
        Aspect : 핵심 비즈니스 로직과는 별도로 수행되는 횡단 관심사를 말한다.
        Advice : Aspect의 기능 자체를 말한다.
                    - Advice의 종류
                    - Before : 대상 메소드가 실행되기 이전에 실행되는 어드바이스
                    - After-returning : 대상 메소드가 정상적으로 실행된 이후에 실행되는 어드바이스
                    - After-throwing : 예외가 발생했을 때 실행되는 어드바이스
                    - After : 대상 메소드가 실행된 이후에(정상, 예외 관계없이) 실행되는 어드바이스
                    - Around : 대상 메소드 실행 전/후에 적용되는 어드바이스
        Join point : Advice가 적용될 수 있는 위치를 말한다.
        Pointcut: Join point 중에서 Advice가 적용될 가능성이 있는 부분을 선별한 것을 말한다.
                    -> pointcut 표현식 특이
        Weaving : Advice를 핵심 비즈니스 로직에 적용하는 것을 말한다.
        보안을위해 시큐리티 체크하거나 (DB에서 배운) transaction 할 때 등의 경우에 AOP를 쓴다.
        */

        //5. Application write
        ApplicationContext context = new AnnotationConfigApplicationContext("com.ohgiraffers/aop");

        MemberService memberService = context.getBean("memberService", MemberService.class);
        // Baen ID와 메타정보를 전부 넘겨서 확인

        System.out.println("===== selectMembers=====");
        memberService.selectMembers(); //selectMembers 메소드 실행
        System.out.println(memberService.selectMembers());

        System.out.println("===== selectMember=====");
        memberService.selectMember(1L); // selectMember 메소드 실행
        System.out.println(memberService.selectMember(2L)); //MemberDTO{ID=2, NAME='홍길동'}

    }
}