package com.ohgiraffers.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//5. InterceptorTestController class는 냅두고(더 이상 건드리지 않고) 따로 StopWatchController 클래스 만들기
@Component
public class StopWatchInterceptor implements HandlerInterceptor {
    //7-1. menuService 불러오기 위해 final로 불러주고 빨간 줄 해결 위해 ALT+INSERT로 constructor 만들어줌
    private final MenuService menuService;

    public StopWatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    //5-1. ALT+INSERT overriding method로 아래 3가지 메소드 넣기
    /*Handler method 호출 이전에 동작하는 메소드(전처리 메소드)*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Call the preHandler...");
        long startTime = System.currentTimeMillis();
        // 8. postHandle에서 사용할 수 있도록, preHandle에서 측정한 시작 시간을 request에 담아 전달한다.
        //      preHandle과 postHandle은 서로 다른 시점에 호출되므로, (두 메서드는 서로 호출 스코프가 다르기 때문에)
        //      데이터를 공유하려면 request의 attribute를 이용해야 한다.
        request.setAttribute("startTime", startTime);
        return true;
    }
    /*핸들러 메소드 동작 이후에 수행되는 메소드(후처리 메소드)*/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        request.removeAttribute("startTime");
        // 8-1. 요청 처리 시간을 계산한 후, result.html에서 사용할(명명해놨던) 변수 interval에 저장한다.
        //      interval에 endTime - startTime이란 값을 modelAndView.addObject를 통해 저장.
        //      interval은 method 호출 수행 시간을 의미한다.
        // (postHandle에서 startTime과 endTime의 차이를 계산해서 interval이라는 이름으로 ModelAndView에 저장,
        // 이 값이 없으면 템플릿(result.html)에서 출력 불가)
        modelAndView.addObject("interval", endTime - startTime);

        System.out.println("Call the postHandler...");
    }

    /*요청을 했을 때 성공 실패 관계 없이 무조건 동작하는 메소드*/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Call the afterCompletion...");
        //8-2. menuService.method(); 추가
        menuService.method();
    }
}
