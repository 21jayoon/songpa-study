package com.ohgiraffers.handlermethod;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/*스프링 웹 프로젝트에서 클래스를 만들고 @Controller라는 어노테이션을 붙이면,
이 클래스는 "컨트롤러"라는 역할을 하게 돼요.
컨트롤러는 클라이언트(브라우저 등)가 보낸 요청(예: regist, modify 등)을 받아서,
어떤 처리를 할지 결정하고, 그 결과를 화면(View)에 전달해주는 역할이에요.

쉽게 말해서,
"누가 문을 두드리면(요청), 문을 열고(컨트롤러), 안에서 처리하고(로직), 대답을 해주는(뷰 반환)"
그런 흐름이라고 생각하면 돼요.*/
@Controller
/* 1. WebRequest로 요청 파라미터 전달받기
 * 파라미터 선언부에 WebRequest 타입을 선언하면 해당 메소드 호출 시 인자로 값을 전달해줌
 * 핸들러 메소드 매개변수로 HttpServletRequest, HttpServletResponse도 사용가능해진다.
 * 상위타입은 ServletRequest, SerlvetResponse도 사용 가능하다
 * WebRequest(Spring에 가까운 객체)는
 * HttpServletRequest(Servlet에 종속적임)의 요청 객체가 갖고있는 정보를 거의 다 갖고 있는
 * API로, Servlet에 비종속적(독립적)이다.
 * WebRequest는 Spring의 일부이기 때문에 Spring기반의 프로젝트에서 더 자주 사용된다.
 * */
@RequestMapping("/first/*")
public class FirstController {
    @GetMapping("regist")
    public void regist(){}
    /*컨트롤러 핸들러 메소드의 반환값을 void로 설정하면 요청주소가 view의 이름이 된다.
     * => /first/regist 요청이 들어요면 /first/regist view(화면)를 응답한다. */


    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request){
        /*WebRequest 객체의 getParameter 등의 메소드를 통해 클라이언트로부터 전달된 파라미터를 갖고올 수 있다*/
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        /*클라이언트로부터 전달받은 값을 통해 응답할 화면의 메세지를 생성한다*/
        String msg = name +"을(를) 신규 메뉴 목록의 " + categoryCode+ "번 카테고리에 " +price +"원으로 등록했습니다.";

        model.addAttribute("message", msg);

        return "first/messagePrinter";
    }

    @GetMapping("modify")
    public void modify(){}

    /*2. @RequestParam으로 요청 파라미터 전달받기
    * 요청 파라미터를 매핑하여 호출 시 값을 넣어주는 어노테이션으로 매개 변수 앞에 작성한다
    * form의 name 속성 값과 매개변수의 이름이 다른 경우 @RequestParam("name")을 설정하면 된다.
    * + @RequestParam은 생략 가능하나 명시적으로 작성하는 것이 의미 파악에 쉽다.
    *
    * 전달하는 form의 name속성이 일치하는 것이 없는 경우 400에러가 발생한다.
    * 이는 required 속성의 기본값이 true이기 때문이다.
    * required 속성을 false로 만들면 해당 name 값이 존재하지 않더라도 null로 처리하여
    * 에러가 발생하지 않는다.
    *
    * 값이 넘어오지 않으면 ""빈 문자열이 넘어오게 되는데, 이 때 parsing 관련 에러가 발생할 수 있다.
    * 값이 넘어오지 않는 경우 defaultValue를 이용하게 되면 기본값으로 사용할 수 있다.*/

    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam String modifyName, @RequestParam int modifyPrice){
        String msg = modifyName +"메뉴의 가격을 " +modifyPrice +"원으로 가격을 변경하였습니다.";

        model.addAttribute("message", msg);
        return "first/messagePrinter";
    }

    /*파라미터(매개변수)가 여러개인 경우 맵으로 한 번에 처리할 수도 있다.
    * 이 때 맵의 키는 form의 name 속성값이 된다*/
    @PostMapping("modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameters){
        String modifyMenu = parameters.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice2"));

        String msg = "메뉴명을 " + modifyMenu+"(으)로, 가격을 " + modifyPrice +"원으로 변경함";

        model.addAttribute("message", msg);
        return "first/messagePrinter";
    }
}
