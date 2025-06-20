package com.ohgiraffers.jpql.section04.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PagingRepositoryTests {
    @Autowired
    private PagingRepository pagingRepository;

    @DisplayName("페이징 api를 이용한 조회 테스트")
    @Test
    void testUsingPagingAPI(){
        int offset = 10;
        int limit = 5;

        List<Menu> menuList = pagingRepository.usingPagingAPI(offset, limit);
        assertTrue(menuList.size() > 0 && menuList.size() < 6);
        menuList.forEach(System.out::println);
    }
}
/* Hibernate:
    select
        m1_0.menu_code,
        m1_0.category_code,
        m1_0.menu_name,
        m1_0.menu_price,
        m1_0.orderable_status
    from
        tbl_menu m1_0
    order by
        m1_0.menu_code desc
    limit
        ?, ?
Menu{menuCode=13, menuName='직화구이젤라또', menuPrice=8000, categoryCode=12, orderableStatus='Y'}
Menu{menuCode=12, menuName='날치알스크류바', menuPrice=2000, categoryCode=10, orderableStatus='Y'}
Menu{menuCode=11, menuName='정어리빙수', menuPrice=10000, categoryCode=10, orderableStatus='Y'}
Menu{menuCode=10, menuName='코다리마늘빵', menuPrice=7000, categoryCode=12, orderableStatus='N'}
Menu{menuCode=9, menuName='홍어마카롱', menuPrice=9000, categoryCode=12, orderableStatus='Y'}
* */