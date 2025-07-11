package com.ohgiraffers.associationmapping.section02.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OnetoManyRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Category find(int categoryCode){
        return entityManager.find(Category.class, categoryCode);
    }

    public void regist(Category category){
        entityManager.persist(category);
    }

}
