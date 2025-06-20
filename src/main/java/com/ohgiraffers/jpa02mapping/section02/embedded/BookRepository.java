package com.ohgiraffers.jpa02mapping.section02.embedded;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    //annotation 이용해 EntityManger 갖고 오기
    @PersistenceContext
    private EntityManager entityManager;
    public void save(Book book){
        entityManager.persist(book);
    }
}
