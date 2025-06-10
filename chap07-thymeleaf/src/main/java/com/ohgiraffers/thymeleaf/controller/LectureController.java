package com.ohgiraffers.thymeleaf.controller;

import com.ohgiraffers.thymeleaf.model.dto.MemberDTO;
import com.ohgiraffers.thymeleaf.model.dto.SelectCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("lecture")
public class LectureController {
    @GetMapping("expression")
    public ModelAndView expression(ModelAndView mv){
        mv.addObject("member", new MemberDTO("Gildong", 20, 'M', "Seochogu Seoul"));
        mv.addObject("hello","hello!<h3>Thymeleaf</h3>");

        mv.setViewName("/lecture/expression");
        return mv;
    }

    @GetMapping("conditional")
    public ModelAndView conditional(ModelAndView mv){
        mv.addObject("num", 1);
        mv.addObject("str", "banana");

        List<MemberDTO> memberList = new ArrayList<>();
        memberList.add(new MemberDTO("Gildong", 20, 'M', "Seoul"));
        memberList.add(new MemberDTO("Gwansun", 27, 'F', "Gyeongi"));
        memberList.add(new MemberDTO("Bogo", 40, 'M', "Jeolla"));
        memberList.add(new MemberDTO("Saimdang", 30, 'F', "Gangwon"));

        mv.addObject("memberList", memberList);

        mv.setViewName("/lecture/conditional"); //<<얘는 생략해도 되긴 하지만 명시적으로 써주는 게 좋으므로 써준다.
        return mv;
    }

    @GetMapping("etc")
    public ModelAndView etc(ModelAndView mv){
        // 여러 페이지 있는 경우 가장 아래에 ◀ 1 2 3 4 5 ▶ 이런 식으로 페이지 넘어갈 수 있게 만든 거 있었잖아?
        //그거 구현하는 method임.
        SelectCriteria selectCriteria = new SelectCriteria(1, 10, 3);

        mv.addObject(selectCriteria);

        return mv;
    }

}
