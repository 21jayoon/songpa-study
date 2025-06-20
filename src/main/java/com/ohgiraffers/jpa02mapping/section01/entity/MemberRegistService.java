package com.ohgiraffers.jpa02mapping.section01.entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberRegistService {
    private MemberRepository memberRepository;

    //의존성 주입 받기 (@Autowired 생략된 생성자 주입 방식 이용)
    public MemberRegistService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void registMember(MemberRegistDTO newMember){

        Member member = new Member(
                newMember.getMemberId(),
                newMember.getMemberPwd(),
                newMember.getMemberName(),
                newMember.getPhone(),
                newMember.getAddress(),
                newMember.getEnrollDate(),
                newMember.getMemberRole(),
                newMember.getStatus()
        );

        memberRepository.save(member);
    }

    @Transactional
    public String registMemberAndFindName(MemberRegistDTO newMember){
        registMember(newMember);
        return memberRepository.findNameById(newMember.getMemberId());
        // newMember.getMemberId() 방금 만든 member의 Id를 찾아와서 해당 Id를 갖고있는 member's name return.
    }

}
