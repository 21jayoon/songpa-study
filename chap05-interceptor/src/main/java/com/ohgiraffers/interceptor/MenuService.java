package com.ohgiraffers.interceptor;

import org.springframework.stereotype.Service;

//7. Service class 생성
@Service //의존성 주입. 인터셉트 bean으로 받아 사용 가능
public class MenuService {
    public void method(){
        System.out.println("service 호출 확인(class MenuSerivce에서)");
    }
}
