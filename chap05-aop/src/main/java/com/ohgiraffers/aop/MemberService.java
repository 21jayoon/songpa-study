package com.ohgiraffers.aop;

import com.ohgiraffers.aop.common.MemberDAO;
import com.ohgiraffers.aop.common.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberService {
    //3. Service 작성(9:42AM)
    private final MemberDAO memberDAO;

    @Autowired
    public MemberService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public Map<Long, MemberDTO> selectMembers () {
        System.out.println("selectMembers 메소드 실행");
        return memberDAO.selectMembers();
    }

    public MemberDTO selectMember(Long id){
        System.out.println("selectMember 메소드 실행");
        return memberDAO.selectMember(id);
    }

}
