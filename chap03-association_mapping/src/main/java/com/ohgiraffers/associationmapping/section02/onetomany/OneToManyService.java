package com.ohgiraffers.associationmapping.section02.onetomany;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OneToManyService {
    //Service는 Repository를 불러오는 역할을 하기 때문에 생성자 방식으로 의존성을 주입해준다.
    private OnetoManyRepository onetoManyRepository;

    public OneToManyService(OnetoManyRepository onetoManyRepository){
        this.onetoManyRepository = onetoManyRepository;
    }

    @Transactional
    public Category findCategory(int categoryCode){
        Category category = onetoManyRepository.find(categoryCode);
        System.out.println("Category : " +category);
        return category;
    }

    @Transactional
    public void registCategory(CategoryDTO categoryInfo) {
        Category category = new Category(
                categoryInfo.getCategoryCode(),
                categoryInfo.getCategoryName(),
                categoryInfo.getRefCategoryCode(),
                null
        );
        Menu menu = new Menu(
                categoryInfo.getMenuList().get(0).getMenuCode(),
                categoryInfo.getMenuList().get(0).getMenuName(),
                categoryInfo.getMenuList().get(0).getMenuPrice(),
                categoryInfo.getMenuList().get(0).getCategoryCode(),
                categoryInfo.getMenuList().get(0).getOrderableStatus()
        );
        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        category.setMenuList(menuList);
        onetoManyRepository.regist(category);
    }
}
