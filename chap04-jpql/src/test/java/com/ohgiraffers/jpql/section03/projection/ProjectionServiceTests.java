package com.ohgiraffers.jpql.section03.projection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjectionServiceTests {
    // Repository -> (Service) -> Tests
    // Service class is not necessary

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private ProjectionRepository projectionRepository;

    @DisplayName("단일 엔티티 projection")
    @Test
    void testSingleEntityProjection(){
        List<Menu> menuList = projectionService.singleEntityProjection();
        System.out.println(menuList);
        assertNotNull(menuList);
    }

    @DisplayName("Scalar스칼라 type projection")
    @Test
    void testScalarTypeProjection(){
        List<Object[]> categoryList = projectionRepository.scalarTypeProjection();
        assertNotNull(categoryList);
        categoryList.forEach(
              row -> {
                  for(Object column : row){
                      System.out.println(column+ " ");
                  }
                  System.out.println();
              }
        ); //foreach 돌리기 위해 object[]배열을 꺼내옴
    }

    @DisplayName("new명령어를 활용한 projection")
    @Test
    void testNewCommandProjection(){
        List<CategoryInfo> categoryInfoList = projectionRepository.newCommandProjection();
        assertNotNull(categoryInfoList);
        categoryInfoList.forEach(System.out::println);
    }
}
