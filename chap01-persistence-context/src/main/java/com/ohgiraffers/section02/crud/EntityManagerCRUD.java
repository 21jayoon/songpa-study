package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Scanner;

public class EntityManagerCRUD {
    //엔티티 매니저를 갖고 조회하고 저장한다. 그러므로 엔티티 매니저 필요함
    private EntityManager entityManager;

    /*1. 특정메뉴코드로 메뉴 조회하는 기능*/
    public Menu findMenuByMenuCode(int menuCode){
        entityManager = EntityManagerGenerator.getInstance();
        return entityManager.find(Menu.class, menuCode);
        // .find(Menu.class, menuCode);
        // = 메뉴라는 엔티티에서 이런 메뉴코드를 PK로 갖고 있는 엔티티를 찾아와줘요
    }
    Scanner sc = new Scanner(System.in);

    /*2. 새로운 메뉴 저장*/
    public Long saveAndReturnAllCount(Menu newMenu){
        entityManager = EntityManagerGenerator.getInstance();
        // .persist 위에서 transaction 처리 필요
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin(); //start transaction

        entityManager.persist(newMenu);

        //'저장한 뒤 transaction을 commit하겠습니다'라는 뜻
        entityTransaction.commit();

        return getCount(entityManager);  //같은 클래스 내에 getCount 메소드 만들어줌
    }

    /*메뉴 개수 조회 기능*/
    private Long getCount(EntityManager entityManager) {
        /*JPQL 문법 -> 나중에 다른 챕터에서 다룰 예정. 오늘은 간단하게만 사용*/
        return entityManager.createQuery("SELECT COUNT(*) FROM Section02Menu"
                                            , Long.class).getSingleResult();
    }

    /*3. 메뉴 이름 수정하기*/
    public Menu modifyMenuName(int menuCode, String menuName){
        entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu  = entityManager.find(Menu.class, menuCode); //일단 Menu와 menuCode를 찾아온다
        //.find is working with PK

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        foundMenu.setMenuName(menuName);

        transaction.commit();

        return foundMenu;  //이름 바꾼 메뉴를 return 해준다.
    }

    /*4. 메뉴 삭제하기*/
    public Long removeAndReturnAllCount(int menuCode){
        //Why is this Long? not a menu? ->
        entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //User remove method to delete
        entityManager.remove(foundMenu);
/*Transaction not successfully started
java.lang.IllegalStateException: Transaction not successfully started
	at org.hibernate.engine.transaction.internal.TransactionImpl.commit(TransactionImpl.java:95)
	at com.ohgiraffers.section02.crud.EntityManagerCRUD.removeAndReturnAllCount(EntityManagerCRUD.java:70)*/
        transaction.commit();
        return getCount(entityManager);
        //메뉴 삭제 후 전체 열이 몇 개인지(?) 세기 위해 Long을 넣었던 것. 그리고 getCount(entityManager)를 넣어
        //실제 횟수를 세게 만든다
    }
}
