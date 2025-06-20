package com.ohgiraffers.jpql.section03.projection;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectionService {
    private ProjectionRepository projectionRepository;

    public ProjectionService(ProjectionRepository projectionRepository){
        this.projectionRepository = projectionRepository;
    }

    @Transactional
    public List<Menu> singleEntityProjection() {
        List<Menu> menuList = projectionRepository.singleEntityProjection();

        // 엔티티 프로젝션은 영속성 컨텍스트에서 관리하는 객체가 된다.
        menuList.get(1).setMenuName("Good Bingsu");
        return menuList;
    }

}
