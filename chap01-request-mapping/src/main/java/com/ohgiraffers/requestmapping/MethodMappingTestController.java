package com.ohgiraffers.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*DispatcherServlet은 웹 요청을 받는 즉시 @Controller가 달린 컨트롤러 클래스에 처리를 위임한다
* 그 과정은 컨트롤러 클래스의 핸들러 메소드에 선언된 다양한 @RequestMapping 설정 내용에 따른다.*/
@Controller
public class MethodMappingTestController {
    //1) @Controller 어노테이션으로 Ioc 컨테이너에 Bean으로 등록

    /*1. 메소드 방식 미지정
    * 요청 URL 설정*/
    @RequestMapping("/menu/regist") //@RequestMapping : 요청을 매핑하겠다는 뜻. 어디로 매핑? /menu/regist로.
    public String registMenu(Model model){
        /*Model 객체에 addAttribute method를 이용해
        * key, value를 추가하면 추후 view에서 사용할 수 있다.*/

        model.addAttribute("message","신규 메뉴 등록용 핸들러 메소드 호출함");
        return "mappingResult"; //2) mappingResult page를 만들어서 forwarding해줄 것임
    }


    /*2. 메소드 방식 지정
    * 요청URL을 value 속성에, request method를 method 속성에 설정(=요청 method를 보냈을 때만 처리하도록 설정)*/
    @RequestMapping(value = "/menu/modify",method = RequestMethod.GET)
    public String modifyMenu(Model model){
        model.addAttribute("message", "GET 방식의 메뉴 수정용 핸들러 메소드 호출함");
        return "mappingResult";
    }

    @RequestMapping(value = "/menu/modify", method = RequestMethod.POST)
    public String modifyMenuByPost(Model model){
        model.addAttribute("message", "Call the POST type menu modifying handler method");
        return "mappingResult";
    }

    /*3. 요청 메소드 전용 어노테이션
    * 요청 메소드     어노테이션
    * GET           @GetMapping
    * POST          @PostMapping
    * PUT           @PutMapping
    * DELETE        @DeleteMapping
    * PATCH         @PatchMapping
    * 이 어노테이션들은 @RequestMapping 어노테이션에 method 속성을 사용해 요청 방법을 지정하는 것과 같다.
    * 각 어노테이션은 해당하는 요청 메소드에 대해서만 처리할 수 있도록 제한하는 역할을 한다.*/

    @GetMapping("/menu/delete")
    public String getDelete(Model model){
        model.addAttribute("message", "GET 방식의 삭제용 핸들러 메소드 호출함");
        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDelete(Model model){
        model.addAttribute("message", "Post 방식의 삭제용 핸들러 메소드 호출");
        return "mappingResult";
    }
}
