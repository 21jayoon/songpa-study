package com.ohgiraffers.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/*Ioc 컨테이너가 알 수 있게 controller으로 등록*/
@Controller
@RequestMapping("/order/*") // 상위 요청(상위 매핑) : /order 하위에 있는 어떤 요청이건 매핑해주겠다(매핑 불러오겠다)
public class ClassMappingTestController {
    /*1. Class 레벨 매핑*/
    @GetMapping("/regist")
    public String registOrder(Model model){
        model.addAttribute("message", "GET방식의 주문 등록용 핸들러 메소드 호출");
        return "mappingResult";
    }

    /*2. 여러 개의 패턴 매핑*/
    @PostMapping(value={"modify", "delete"})
    //value = {"modify", "delete"} = /order/modify나 /order/delete로 들어왔을 때 두 가지 경우 모두 아래 메소드에서 다룰 것이다
    public String modifyAndDelete(Model model){
        model.addAttribute("message", "POST방식의 주문 정보 수정과 삭제 메소드 동시 호출함");
        return "mappingResult";
    }

    /*3. path variable
    * @PathVariable annotation을 이용해 요청path로부터 변수를 받아올 수 있다.
    * path variable로 전달되는 {변수명} 값은 반드시 매개변수명과 동일해야 한다.
    * 만약 동일하지 않으면 @PathVariable("이름")을 설정해줘야 한다.
    * 이는 Rest형 웹 서비스를 설계할 때 유용하게 사용된다.*/
    @GetMapping("/detail/{orderNo}")
    public String selectOrderDeatil(Model model, @PathVariable int orderNo) {
        /*Parsing 불가능한 PathVariable이 전달되면 400번 에러가 발생.
        * @PathVariable이 없으면 해당 핸들러 메소드를 찾지 않는다.*/
        model.addAttribute("message", orderNo+"번 주문 상세 내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }
}
