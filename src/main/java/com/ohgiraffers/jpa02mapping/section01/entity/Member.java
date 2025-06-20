package com.ohgiraffers.jpa02mapping.section01.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//Entity class로 할 거니까 annotation달기 =>DB와 mapping될 것임을 미리 알려줌
@Entity(name = "entityMember")
@Table(name = "tbl_member")   //tbl_member라는 테이블이 생성되도록
@Access(AccessType.FIELD) //@Access(AccessType.FIELD)는 클래스 레벨에 설정 가능, 모든 필드를 대상으로 적용하겠다는 의미
public class Member {
    //entity 등록할 때는 PK가 필수이다.
    // PK를 만들거나 @Id를 통해 불러와주지 않으면 method이름 아래에 계속 빨간 줄이 나온다
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    private int memberNo;

    @Access(AccessType.FIELD)
    @Column(
            name = "member_id", unique = true,
            nullable = false, columnDefinition = "varchar(10)"
    )
    private String memberId;

    @Column(name = "member_pwd", nullable = false)
    private String memberPwd;

    @Access(AccessType.PROPERTY)
    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address", length = 900)
    private String address;

    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(name = "status", columnDefinition = "char(1) default 'Y'")
    private String status;

    //기본생성자 필수 작성
    protected Member(){}

    public Member(String memberId, String memberPwd, String memberName, String phone, String address, LocalDateTime enrollDate, MemberRole memberRole, String status) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.phone = phone;
        this.address = address;
        this.enrollDate = enrollDate;
        this.memberRole = memberRole;
        this.status = status;
    }
    
    @Access(AccessType.PROPERTY)
    public String getMemberName(){
        System.out.println("Check the access through getMemberName method");
        return memberName + "님";
    } //프로퍼티 방식으로 getter 메소드에 접근하는 걸 확인하기 위한 메소드
    
    public void setMemberName(String memberName){
        this.memberName = memberName;
    } 

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDateTime enrollDate) {
        this.enrollDate = enrollDate;
    }

    public MemberRole getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Member{" +
                " memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", memberName='" + memberName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", enrollDate=" + enrollDate +
                ", memberRole=" + memberRole +
                ", status='" + status + '\'' +
                '}';
    }
}
