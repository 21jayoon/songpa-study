package com.ohgiraffers.section01.entitymanager;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryGenerator {
        private static EntityManagerFactory factory
                = Persistence.createEntityManagerFactory("jpatest");
        //singletone으로 관리하면 좋다 -> static 붙임
        // persistence.xml에 있는 <persistence-unit name="jpatest">과 이름 동일

        //생성자도 private으로 declare
        private EntityManagerFactoryGenerator() {}

        public static EntityManagerFactory getInstance(){
            return factory;
        }
        //application scope로 만드는 게 목적이기 때문에 (필요할 때 여기서 갖다 쓰기 위해) 위와 같이 코드를 작성함
        //여기까지 작성하고 잘 동작되는지 아래 test 폴더에 가서 시험해볼 거임 10:50 0617
}