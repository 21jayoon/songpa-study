package com.ohgiraffers.jpql.section03.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /*SELECT 절에 조회할 대상을 지정하는 것을 프로젝션이라고 한다.
    (SELECT {프로젝션 대상} FROM)

    프로젝션 대상은 4가지 방식이 있다.
    1. 엔터티 프로젝션 : 원하는 객체를 바로 조회할 수 있다. 조회된 엔티티는 영속성 컨텍스트가 관리한다.
    2. 임베디드 타입 프로젝션 : 조회의 시작점이 될 수 없다. (from 절 사용 불가)
                            임베디드 타입은 영속성 컨텍스트에서 관리되지 않는다.
    3. 스칼라 타입 프로젝션 : 숫자, 문자, 날짜 같은 기본 데이터 타입이다.
                           스칼라 타입은 영속성 컨텍스트에서 관리되지 않는다.
    4. new 명령어를 활용한 프로젝션 : 다양한 종류의 단순 값들을 DTO로 바로 조회하는 방식.
                        'new 패키지명.DTO명'을 쓰면 해당 DTO로 바로 반환받을 수 있다.
                         new 명령어를 사용한 클래스의 객체는 엔티티가 아니므로 영속성 컨텍스트에서 관리되지 않는다.
     */

    public List<Menu> singleEntityProjection() {
        String jpql = "SELECT m FROM Section03Menu m";
        //entity projection 했을 때 영속성 컨텍스트에 관리가 되는가 확인하기 위한 코드
        //-> 엔티티 프로젝션은 영속성 컨택스트에서 관리가 된다
        List<Menu> menuList
                = entityManager.createQuery(jpql, Menu.class).getResultList();
        return menuList;
    }

    public List<Object[]> scalarTypeProjection(){
        String jpql = "SELECT c.categoryCode, c.categoryName FROM Section03Category c";
        return entityManager.createQuery(jpql).getResultList();
    }

    public List<CategoryInfo> newCommandProjection(){
        //com.ohgiraffers.jpql.sectio03.projection.CategoryInfo = 생성자 호출
        String jpql = "SELECT new com.ohgiraffers.jpql.section03.projection.CategoryInfo(c.categoryCode, c.categoryName)" +
                "FROM Section03Category c";
        List<CategoryInfo> resultList = entityManager.createQuery(jpql, CategoryInfo.class).getResultList();
        return resultList;
    }
}
