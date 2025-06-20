package com.ohgiraffers.jpql.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleJPQLRepository {
    //now you can learn how to write jpql

    @PersistenceContext
    private EntityManager entityManager;

    public String selectSingleMenuByTypedQuery(){
        String jpql = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
        // you can ignore(?) as in Section01Menu as m (FROM Section01Menu m is also OK)
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        //entityManager..createQuery(1, 2) 1: 수행하고자 하는 jpql 문법(SQL구문), 2: 반환받을 자료형 타입
        //TypedQuery<String> : String TypedQuery로 반환 받겠다.
        String resultMenuName = query.getSingleResult();
        /* .getSingleResult() 행 하나 조회할 때 사용
        * .getResultList() 행 여러 개 조회할 때 사용 */
        return resultMenuName;
    }

    public List<Menu> selectMultiRowByTypedQuery(){
        String jpql = "SELECT m FROM Section01Menu as m";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        // .createQuery(jpql, Menu.class) String 말고 Menu (행 여러 개) 형태로 받아온다.
        // 따라서 TypedQuery도 <Menu>로 바꿔야하고,
        List<Menu> resultMenuList = query.getResultList();
        // 출력할 때도 SingleResult 말고 .getResultList()를 사용해 여러 행을 받아올 수 있도록 해야한다.

        return resultMenuList;
    }

    public List<Integer> selectUsingDistinct(){
        String jpql = "SELECT DISTINCT m.categoryCode FROM Section01Menu m";
        // DISTINCT : 쿼리에서 중복값을 제거하는 단어
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> resultCategoryList = query.getResultList();
        return resultCategoryList;
    }


    //실습 11:08-
    /*11, 12 카테고리 코드를 가진 메뉴목록 조회*/
    public List<Menu> selectUsingIn(){
        String jpql = "SELECT m FROM Section01Menu m WHERE m.categoryCode IN (11, 12)";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> specificCategoryList = query.getResultList();
        return specificCategoryList;
    }

    /*"마늘"이란 문자열이 메뉴명에 포함되는 메뉴 목록 조회*/
    public List<Menu> selectUsingLike(){
        String jpql = "SELECT m FROM Section01Menu m WHERE m.menuName LIKE '%마늘%'";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> specificMenuList = query.getResultList();
        return specificMenuList;
    }
}
