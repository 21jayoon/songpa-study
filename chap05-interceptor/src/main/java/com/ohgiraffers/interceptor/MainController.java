package com.ohgiraffers.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 2. MainController 만들기(@Controller로 Bean등록시키고 @RequsetMapping에서 상위매핑해주기)
@Controller
@RequestMapping("/*")
public class MainController {

    // /main 페이지로 forwarding (어제6.9와는 다른 방법)
    @RequestMapping("/")
    public String defaultLocation(){
        return "main";
    }
    @RequestMapping("/main")
    public void main(){}
}
