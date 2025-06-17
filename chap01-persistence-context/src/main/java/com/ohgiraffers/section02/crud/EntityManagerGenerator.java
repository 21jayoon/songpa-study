package com.ohgiraffers.section02.crud;

import com.ohgiraffers.section01.entitymanager.EntityManagerFactoryGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerGenerator {
    private static EntityManagerFactory factory
            = Persistence.createEntityManagerFactory("jpatest");

    private EntityManagerGenerator(){}

    public static EntityManager getInstance(){
        return factory.createEntityManager();
        // entity manager는 요청이 생길 때마다 다른 객체로 생성해줘야함.
        // entity manager가 하나 만들어질 때 PersistenceContext도 하나 생성된다.
    }
}
