package com.ohgiraffers.jpa02mapping.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

//@Repository = DB에 접근하는 클래스들이 Bean으로 등록되어야 할 때(?) 달아주는 어노테이션
@Repository //bean registered
public class MemberRepository {
    @PersistenceContext  //어노테이션 붙여서 EntityManager 사용가능케 만듦
    private EntityManager entityManager;

    public void save(Member member){
        entityManager.persist(member);  //전달받은 member 엔티티를 persist(저장)한다
    }

    // 20250619 9:30 vvv
    public String findNameById(String memberId){
        // Use JPQL
        String jpql = "SELECT m.memberName FROM entityMember m WHERE m.memberId = '" + memberId + "'";
        // JPQL 특징 : FROM에 들어가는 것이 엔티티 이름(e.g. entityMember)이 됨. (Not a table name)
        // Also, alias(별칭) is essential. (e.g. entityMember "m" in upper code)
        return entityManager.createQuery(jpql, String.class).getSingleResult();
    }
}
