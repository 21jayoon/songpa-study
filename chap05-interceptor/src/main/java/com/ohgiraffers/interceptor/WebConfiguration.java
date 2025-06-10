package com.ohgiraffers.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//6. web에 대한 설정을 만드는 WebConfiguration class 생성
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //6-1. InterceptorRegistry는 IoC컨테이너가 갖고 있는 인터셉터 저장소라고 생각하면 됨
        registry.addInterceptor(new StopWatchInterceptor(new MenuService()))
        //6-2. 등록까지 해줘야 Interceptor class 사용가능해짐.
        //예) 위에처럼 Configuration에 StopWatchInterceptor를 registry에 .addInterceptor통해 등록할거야!
        // 한 이후에나 interceptor 사용가능
                //9./*를 사용해 모든 요청을 인터셉트 거치도록 처리해준다.
                .addPathPatterns("/*")
                // 9-1. static 하위의 정적인 리소스는 인터셉터를 거치지 않게 예외 처리해준다.
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/js/**")
                // 9-2. 에러 페이지(/error) 요청도 인터셉터를 적용하지 않도록 제외한다.
                .excludePathPatterns("/error");
    }
}
