package com.ohgiraffers.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//3. InterceptorTestController class 만들기
@Controller
@RequestMapping("/*")
public class InterceptorTestController {
    @GetMapping("stopwatch")
    public String handlerMethod() throws InterruptedException {

        System.out.println("Call the handler method in class Intercept..Controller");
        Thread.sleep(1000); // 1000이 1초
        return "result";  // 4. /result페이지 만들어서 이 페이지로 forward시킴
    }
}
