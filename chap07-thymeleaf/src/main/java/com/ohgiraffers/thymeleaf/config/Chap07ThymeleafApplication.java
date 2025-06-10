package com.ohgiraffers.thymeleaf.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Chap07ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap07ThymeleafApplication.class, args);
    }
    /*thymeleaf package 안에 LectureController랑 Chap07ThymeleafApplication가 같이 있을 땐 정상 작동되었는데
    * thymeleaf 아래에 config 패키지와 controller 패키지로 나눠서 각각에 Chap07ThymeleafApplication,
    * LectureController 클래스를 넣어줬더니 Bean어쩌구 오류가 난다
    * 왜?? Chap07ThymeleafApplication에 붙은 어노테이션 @SpringBootApplication에는
    * @ComponentScan이 자동으로 들어가있는데
     */

}
