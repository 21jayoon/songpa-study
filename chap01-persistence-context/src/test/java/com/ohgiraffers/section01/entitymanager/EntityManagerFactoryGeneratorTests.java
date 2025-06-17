package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerFactoryGeneratorTests {
    @Test
    @DisplayName("Test if the entity-manager-factory generated")
    void testGenerateEntityManagerFactory(){
        //기본이 public(?)이라 생략하고 void만 작성해줌

        /*test의 3가지 스텝 : given, when, then*/
        //given

        //when
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

        //then
        assertNotNull(factory);
    }
    @Test
    @DisplayName("Test if the entity-manager-factory is a singleton instance")
    void testSingletonEntityManagerFactory(){
        /*test의 3가지 스텝 : given, when, then*/
        //given

        //when
        EntityManagerFactory factory1 = EntityManagerFactoryGenerator.getInstance();
        EntityManagerFactory factory2 = EntityManagerFactoryGenerator.getInstance();

        //then
        assertEquals(factory1, factory2);
    }

    @Test
    @DisplayName("Test if the entity-manager generated")
    void testGenerateEntityManager(){
        //기본이 public(?)이라 생략하고 void만 작성해줌

        /*test의 3가지 스텝 : given, when, then*/
        //given

        //when
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        //then
        assertNotNull(entityManager);
    }

    @Test
    @DisplayName("Test if the entity-manager's scope")
    void testEntityManagerLifeCycle(){
        /*test의 3가지 스텝 : given, when, then*/
        //given

        //when
        EntityManager entityManager1 = EntityManagerGenerator.getInstance();
        EntityManager entityManager2 = EntityManagerGenerator.getInstance();

        //then
        assertNotEquals(entityManager1, entityManager2);
        // entityManager1 객체와 entityManager2 객체는 각각 다른 객체임
    }
}
