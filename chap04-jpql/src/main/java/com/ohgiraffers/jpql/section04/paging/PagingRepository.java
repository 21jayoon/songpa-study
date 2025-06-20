package com.ohgiraffers.jpql.section04.paging;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PagingRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Menu> usingPagingAPI(int offset, int limit){
        String jpql = "SELECT m FROM Section04Menu m ORDER BY m.menuCode DESC";
        //너무 많은 개수의 결과물을 출력할 경우 이걸 잘라서 보여주는 걸 '페이징처리'한다고 한다
        List<Menu> pagingMenuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset)  //조회를 어디에서부터 시작할 것이냐 지정해주는 메소드 (조회시작할 위치는 0부터 시작 가능)
                //offset
                .setMaxResults(limit)  // 조회하고자 하는 데이터의 개수
                // limit
                .getResultList();
        return pagingMenuList;
    }
}
