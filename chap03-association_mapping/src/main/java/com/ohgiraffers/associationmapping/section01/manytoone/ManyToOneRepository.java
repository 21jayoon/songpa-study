package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Menu find(int menuCode){
        return entityManager.find(Menu.class, menuCode);
    }

    public String findCategoryName(int menuCode){
        // 매개변수로 menuCode를 전달받아서 어떤 categoryName을 갖고 있는지 갖고오는 메소드
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = :menuCode";
        //jpql은 entity를 대상(기준)으로 한다. 그리고 엔티티의 별칭 생성이 필수다

        return entityManager.createQuery(jpql, String.class).setParameter("menuCode", menuCode).getSingleResult();
        // .crea...(jpql, String.class) :
        // 반환받을 값이 category name이므로 String 타입(문자열)으로 반환 받겠습니다.
    }

    public void regist(Menu menu){
        entityManager.persist(menu);
    }

}
