package com.ohgiraffers.associationmapping.section03.bidirection;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BiDirectionService {
    // constructor dependency injection
    private BiDirectionRepository biDirectionRepository;

    public BiDirectionService(BiDirectionRepository biDirectionRepository){
        this.biDirectionRepository = biDirectionRepository;
    }

    public Menu findMenu(int menuCode){
        return biDirectionRepository.findMenu(menuCode);
    }

    @Transactional
    public Category findCategory(int categoryCode){
        Category category = biDirectionRepository.findCategory(categoryCode);
        System.out.println(category.getMenuList());
        System.out.println(category.getMenuList().get(0).getCategory());
        return category;
    }
    /* findCategory는 Category 엔티티를 조회하는 메서드로,
     Category의 menuList 필드는 LAZY(지연로딩)로 설정되어 있음.
     (원래 카테고리를 찾아오는 것 자체는 lazy로딩이 일어나는 건데, (카테고리가 지연로딩을 갖고 있음))
     따라서 category만 조회할 땐 menuList가 실제로 DB에서 조회되지 않고, menuList에 접근하는 시점에 쿼리가 실행됨.
     menuList를 조회할 때 영속성 컨텍스트가 열려 있어야 지연로딩이 동작해서 쿼리가 수행됨.
     만약 @Transactional이 없으면 서비스 메서드 실행 후 영속성 컨텍스트가 닫혀있어
     menuList를 조회할 때 LazyInitializationException이 발생함. (카테고리를 조회 했을 때 지연로딩이라서 메뉴 조회가 안 됨.)
     즉, @Transactional이 있어야 category와 menuList 모두 정상적으로 조회 가능함.*/
}