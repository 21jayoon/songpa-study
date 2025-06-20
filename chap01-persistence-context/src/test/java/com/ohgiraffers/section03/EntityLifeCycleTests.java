package com.ohgiraffers.section03;

import com.ohgiraffers.section03.entity.EntityLifeCycle;
import com.ohgiraffers.section03.entity.EntityManagerGenerator;
import com.ohgiraffers.section03.entity.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class EntityLifeCycleTests {
    private EntityLifeCycle lifeCycle;

    @BeforeEach
    void setup(){
        this.lifeCycle = new EntityLifeCycle();
    }

    //entitycontext가 관리하지 않는 상내 = 비영속

    @DisplayName("비영속 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2})
    void testTransient(int menuCode){
        //when
        Menu foundMenu = lifeCycle.findeMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );

        EntityManager entityManager = lifeCycle.getManagerInstance();
        //현재 사용중인 엔티티 매니저의 반환 값을 받았다


        //then
        assertNotEquals(foundMenu, newMenu);
        assertTrue(entityManager.contains(foundMenu));
        assertFalse(entityManager.contains(newMenu));
    }

    @DisplayName("다른 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {3})
    void testManagedOtherEntityManager(int menuCode){
        //when
        Menu menu1 = lifeCycle.findeMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findeMenuByMenuCode(menuCode);
        //menu1과 menu2는 다르다

        //then
        assertNotEquals(menu1, menu2);
        /*when you see the console, you can see the query executed twice.
        so the menu1 and menu2 is different.
        * Hibernate:
    select
        m1_0.menu_code,
        m1_0.category_code,
        m1_0.menu_name,
        m1_0.menu_price,
        m1_0.orderable_status
    from
        tbl_menu m1_0
    where
        m1_0.menu_code=?
Hibernate:
    select
        m1_0.menu_code,
        m1_0.category_code,
        m1_0.menu_name,
        m1_0.menu_price,
        m1_0.orderable_status
    from
        tbl_menu m1_0
    where
        m1_0.menu_code=? */
    }

    @DisplayName("Same EntityManager가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {3})
    void testManagedSameEntityManager(int menuCode) {
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        //when
        Menu menu1 = lifeCycle.findeMenuByMenuCode(menuCode);
        // menu1을 통해 db에서 찾아온 3번(key)이라는 객체의 값(value)을 Persistence Context에 넣어준다.
        Menu menu2 = lifeCycle.findeMenuByMenuCode(menuCode);
        // menu2에서는 이미 3번이라는 객체가 Persistence Context에 있기 때문에 3번을 그냥 꺼내와만 준다.
        // 그러므로 여기서 menu1과 menu2는 같고 둘은 영속성을 가진다

        //then
        assertNotEquals(menu1, menu2);
    }

    @DisplayName("준영속화 detach test")
    @ParameterizedTest
    @CsvSource("11,1000")
    void testDetachEntity(int menuCode, int menuPrice){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        //when
        entityTransaction.begin();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        //menuCode가 11번은 걸 Menu db에서 찾아오겠습니다

        //detach: 특정 엔티티만 준영속 상태**로 만든다.
        // ** 영속성 컨텍스트가 관리하던 객체를 관리하지 않는(연결이 끊긴) 상태
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);
        //MenuPrice를 찾아온 뒤 전달 받은 값(1000)으로 set해준다(수정해준다)

        //flush : 영속성 컨텍스트의 상태를 DB에 내보낸다. commit하지 않은 상태이므로 rollback available.
            //commit : flush 기능 내포. but when you 'commit', you cannot do rollback(되돌리기).
        entityManager.flush();

        //Compare menuPrice vs entityManager.find(Menu.class, menuCode).getMenuPrice();
        // detach 했다가 다시 entityManager.find를 하면, 중간에 detach로 entityManager 내에서의
        // 연결이 끊겼었기 때문에 entityManager.find를 한 그 시점에 다시 DB로부터 11번(key)의 값을 갖고온다.
        // 그러면 11번의 원래 menuPrice 값인 10000원을 갖고 오게 되고
        // 이미 위에서 foundMenu.setMenuPrice(menuPrice);로 price를 1000원으로 바꿔준
        // entityManager에서 관리가 안 됐던 11번(detached 상태의 11번 엔티티)과 다른 값이 된다.

        //'임의의 11번'이라는 표현은 조금 모호할 수 있습니다. -임의의 11번 -> eM에서 관리가 안 됐던 11번으로 변경
        //여기서 11번은 menuCode가 11인 엔티티인데, detach 이후에 값을 바꾼 것이죠.
        //detach 상태의 엔티티는 DB나 EntityManager가 관리하는 값이 아니라,
        // 오직 자바 객체 안에서만 값이 바뀐 상태입니다.
        /* The first attached entity doesn’t disappear—
         it just stops "being watched" by the EntityManager when you detach.
        If you change its value, only your copy in memory(detached entity) knows about it.

         */

        //then
        assertNotEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        entityTransaction.rollback();
    }

    @DisplayName("준영속화 detach 후 다시 영속화 테스트")
    @ParameterizedTest
    @CsvSource("11,1000")
    void testDetachAndMerge(int menuCode, int menuPrice){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        //when
        entityTransaction.begin();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        //menuCode가 11번은 걸 Menu db에서 찾아오겠습니다

        //detach: 특정 엔티티만 준영속 상태**로 만든다.
        // ** 영속성 컨텍스트가 관리하던 객체를 관리하지 않는(연결이 끊긴) 상태
        entityManager.detach(foundMenu);
        // foundMenu entity만 준영속 상태로 만들었다.
        foundMenu.setMenuPrice(menuPrice);

        //merge를 통해 준영속성 상태를 영속성 상태로 되돌릴 수 있다
        entityManager.merge(foundMenu);
        entityManager.flush();

        //then
        assertEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        // 둘은 값이 같다.
        entityTransaction.rollback();
    }
    @DisplayName("Test update after merging detached data")
    @ParameterizedTest
    @CsvSource("11,soup")
    void testDetachAndMerge(int menuCode, String menuName){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        entityManager.detach(foundMenu);

        //when
        foundMenu.setMenuName(menuName);
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);

        entityManager.merge(foundMenu);

        //then
        assertEquals(menuName, refoundMenu.getMenuName());
        //Expected :soup
        //Actual   :정어리빙수
        //<Click to see difference>
        //
        //org.opentest4j.AssertionFailedError: expected: <soup> but was: <정어리빙수>
        //>> ???을 위해   entityManager.merge(foundMenu); 를 붙이고 다시 한 번 run
    }

    @DisplayName("준영속화 clear test")
    @ParameterizedTest
    @ValueSource(ints={6})
    void testClearPersistenceContext(int menuCode){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        entityManager.clear();

        //then
        Menu expectedMenu = entityManager.find(Menu.class, menuCode);
        assertNotEquals(foundMenu, expectedMenu);
    }

    @DisplayName("준영속화 close test")
    @ParameterizedTest
    @ValueSource(ints={6})
    void testClosePersistenceContext(int menuCode){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        // close : 영속성 컨텍스트를 종료한다.
        entityManager.close();

        //then2
        assertThrows(
                IllegalStateException.class,
                () -> entityManager.find(Menu.class, menuCode)
        );
        //then1
        // Menu expectedMenu = entityManager.find(Menu.class, menuCode);
        //assertNotEquals(foundMenu, expectedMenu); //Error: Session/EntityManager is closed
    }

    @DisplayName("Test Persistence Context erase")
    @ParameterizedTest
    @ValueSource(ints = {6})
    void testRemoveEntity(int menuCode){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        entityTransaction.begin();
        /*remove : 엔티티를 영속성 컨텍스트 및 데이터베이스에서 삭제
        * 단, 트랜젝션을 제어하지 않으면 DB에 영구 반영되지는 않는다.
        * 트랜잭션을 커밋하는 순간 영속성 컨텍스트에서 관리하는 엔티티 객체가 DB에 반영된다*/
        entityManager.remove(foundMenu);

        //flush : 영속성 컨택스트의 변경 내용(여기선 remove)을 데이터 베이스에 동기화하는 작업
        entityManager.flush();
        // DB에 동기화(반영)를 했기 때문에 이 이후에 DB에선 6번 값을 찾을 수 없을 것.
        // = null 값으로 나올 것. 그러나 아직 commit은 안 해서 DB에 영구적으로 반영되진 않음

        //현재 아직 test Run 안 한 상태의 DB: 6,생마늘샐러드,12000,4,Y
        //then
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);
        assertNull(refoundMenu); //여기까지만 쓰고 run했을 때 assertNull 문제 없이 돌아감 = Null이 true라는 것.
        entityTransaction.rollback();
        //DB에 최종적으로 반영하지 않기 위해서 rollback 해준다
        }
}
