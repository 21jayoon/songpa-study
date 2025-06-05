package com.ohgiraffers.handlermethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Chap02HandlerMethodApplication {
    public static void main(String[] args) {
    /*Spring Boot's SpringApplication.run() method
    is the starting point for launching a Spring application.
    왜 Application 클래스가 필요할까?
    Application 클래스는 스프링 부트 애플리케이션의 핵심적인 부분입니다.
    이 클래스를 통해 애플리케이션을 실행하고, 스프링 컨테이너를 시작하며,
    필요한 빈들을 등록합니다.
     Application 클래스는 스프링 부트 애플리케이션이 동작하기 위한 기본적인 구조를 제공하고
     스프링 부트 애플리케이션의 진입점 역할을 합니다. */
        SpringApplication.run(Chap02HandlerMethodApplication.class, args);
    }

}
