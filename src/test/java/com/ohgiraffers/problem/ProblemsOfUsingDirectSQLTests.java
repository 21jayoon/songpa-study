package com.ohgiraffers.problem;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemsOfUsingDirectSQLTests {

    private Connection con;

    @BeforeEach
    void setConnection() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "ohgiraffers";
        String password = "ohgiraffers";

        Class.forName(driver);

        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        //연결객체는 기본 커밋이 자동으로 되도록 설정되어 있기 때문에
        //false 값으로 임의 지정해줌
    }

    @AfterEach
    void closeConnection() throws SQLException {
        con.rollback();
        con.close();
    }

    /* JPA : 자바 영속성 API , ORM 기술: 객체 관계 매핑 기술
    * "JPA로 ORM을 해준다"라고 할 수 있음
    *
    * [ JDBC API를 이용해 직접 SQL을 다룰 때 발생할 수 있는 문제점 ]
    * 1. 데이터 변환, SQL 작성, JDBC API 코드 등을 중복 작성 (= 개발시간 증가, 유지보수성 악화)
    * 2. SQL 의존적인 개발   (OOP 개발 방식, 이 좋은 게 있는데 왜 활용을 못하니!!)
    * 3. 패러다임 불일치 문제 (상속, 연관관계, 객체 그래프 탐색)
    * 4. 동일성 보장 문제
    * */

    /* 1. 데이터 변환, SQL 작성, JDBC API 코드 등을 중복 작성 */
    @Test
    @DisplayName("직접 SQL을 작성하여 메뉴를 조회할 때 발생하는 문제 확인")
    void testDirectSelectSQL() throws SQLException {
        //given
        String query = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS FROM TBL_MENU";

        //when
        Statement stmt = con.createStatement();
        //statement 객체 생성
        ResultSet rset = stmt.executeQuery(query);
        // statement 객체 내에서 query(여기선 SELECT 쿼리)가 실행되고 그것이 rset에 저장됨

        List<Menu> menuList = new ArrayList<>();
        while(rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));
            menu.setMenuPrice(rset.getInt("MENU_PRICE"));
            menu.setCategoryCode(rset.getInt("CATEGORY_CODE"));
            menu.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));

            menuList.add(menu);
        }

        //then
        Assertions.assertNotNull(menuList);

        rset.close();
        stmt.close();
    }

    @Test
    @DisplayName("직접 SQL을 작성하여 신규 메뉴 추가 시 발생하는 문제 확인")
    void testDirectInsertSQL() throws SQLException {
        //given
        Menu menu = new Menu();
        menu.setMenuName("고등어단백질쉐이크");
        menu.setMenuPrice(120000);
        menu.setCategoryCode(1);
        menu.setOrderableStatus("Y");

        String query = "INSERT INTO TBL_MENU(MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS) VALUES (?, ?, ?, ?)";

        //when
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, menu.getMenuName());
        pstmt.setInt(2, menu.getMenuPrice());
        pstmt.setInt(3, menu.getCategoryCode());
        pstmt.setString(4, menu.getOrderableStatus());

        int result = pstmt.executeUpdate();

        //then
        Assertions.assertEquals(1, result);

        pstmt.close();
    }

    /* 2. SQL 의존적인 개발 */

    /* 조회 항목 변경에 따른 의존성 */
    @Test
    @DisplayName("조회 항목 변경에 따른 의존성 확인")
    void testChangeSelectColumns() throws SQLException {
        //given
        String query = "SELECT MENU_CODE, MENU_NAME FROM TBL_MENU";

        //when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<Menu> menuList = new ArrayList<>();
        while(rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));
            menu.setMenuPrice(rset.getInt("MENU_PRICE"));
            //SQL이 변하면 비즈니스로직도 바뀌어야함->SELECT MENU_PRICE 없으니까 
            // 위 menu.setMenuPrice(rset.getInt("MENU_PRICE")); 부분도 삭제해야함
            // 안 그러면 오류남
            //=> SQL(DB)에 의존적이게 된다, DB에 대한 의존성이 높다

            menuList.add(menu);
        }

        //then
        Assertions.assertNotNull(menuList);

        rset.close();
        stmt.close();
    }

    /* 3. 패러다임 불일치 (상속, 연관관계, 객체 그래프 탐색) */
    //RDBMS (관계형 데이터 베이스)와 자바는 패러다임이 불일치함

    /* 3-1. 상속 문제 */
    /*
    * 객체 지향 언어의 상속 개념과 유사한 것이 데이터베이스의 서브타입엔티티 이다.
    * 유사한 것 같지만 다른 부분은 데이터베이스의 상속은 상속 개념을 데이터로 추상화해서 슈퍼타입과 서브타입으로 구분하고,
    * 슈퍼타입의 모든 속성을 서브타입이 공유하지 못하여 물리적으로 다른 테이블로 분리가 된 형태이다.
    * (설계에 따라서는 속성으로 추가되기도 한다.)
    * 하지만 객체지향의 상속은 슈퍼타입의 속성을 공유해서 사용하므로 여기서 패러다임 불일치 현상이 발생한다.
    *
    * 법인 회원과 일반 회원이라는 두 가지 회원을 추상화하여 회원이라는 슈퍼타입 엔터티를 도출하고
    * 서브타입 엔터티로 법인회원과 일반회원이 존재하는 상황인 경우
    * 물리적으로 회원과 법인회원 테이블로 분리된 상황에서는 각각 insert 구문을 수행해야 한다.
    * INSERT INTO 회원 ...
    * INSERT INTO 법인회원 ...
    * 하지만 JPA를 이용하여 상속을 구현한 경우에는
    * entityManager.persist(법인회원);
    * 이렇게 한 번에 해결이 가능하다.
    * */

    /* 3-2. 연관관계 문제, 객체 그래프 탐색 문제 */
    /* DB 기준 객체 모델과 객체지향 언어 기준 객체 모델은 데이터구조가(패러다임이) 다르다.
    * JPA: OOP(객체 지향 프로그래밍) 구현을 좀 더 쉽게 하기 위해 만들어진 인터페이스
    *
    * < 데이터베이스 테이블에 맞춘 객체 모델 >
    * 객체지향에서 말하는 가지고 있는(association 연관관계, 혹은 collection 연관관계) 경우 데이터베이스 저장 구조와 다른 형태이다.
    *   public class Menu {
    *     private int menuCode;
    *     private String menuName;
    *     private int menuPrice;
    *     private int categoryCode; (FK)
    *     private String orderableStatus;
    *   }
    *   public class Category {
    *     private int categoryCode;
    *     private String categoryName;
    *   }
    *
    * < 객체지향 언어에 맞춘 객체 모델 >
    *   public class Menu {
    *     private int menuCode;
    *     private String menuName;
    *     private int menuPrice;
    *     private Category categoryCode;
    *     private String orderableStatus;
    *   }
    *   public class Category {
    *     private int categoryCode;
    *     private String categoryName;
    *   }
    * */
    @Test
    @DisplayName("연관된 객체 문제 확인")
    void testAssociatedObject() throws SQLException {
        //given
        String query = "SELECT A.MENU_CODE, A.MENU_NAME, A.MENU_PRICE, B.CATEGORY_CODE, B.CATEGORY_NAME, A.ORDERABLE_STATUS" +
                " FROM TBL_MENU A" +
                " JOIN TBL_CATEGORY B ON (A.CATEGORY_CODE = B.CATEGORY_CODE)";

        //when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<MenuAndCategory> menuAndCategories = new ArrayList<>();
        while(rset.next()) {
            MenuAndCategory menuAndCategory = new MenuAndCategory();
            menuAndCategory.setMenuCode(rset.getInt("MENU_CODE"));
            menuAndCategory.setMenuName(rset.getString("MENU_NAME"));
            menuAndCategory.setMenuPrice(rset.getInt("MENU_PRICE"));
            menuAndCategory.setCategory(new Category(rset.getInt("CATEGORY_CODE"), rset.getString("CATEGORY_NAME")));
            menuAndCategory.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));

            menuAndCategories.add(menuAndCategory);
        }

        //then
        Assertions.assertNotNull(menuAndCategories);

        rset.close();
        stmt.close();
    }

    /* 4. 동일성 보장 문제 */
    @Test
    @DisplayName("조회한 두 개의 행을 담은 객체의 동일성 비교")
    void testEquals() throws SQLException {
        //given
        String query = "SELECT MENU_CODE, MENU_NAME " +
                "FROM TBL_MENU WHERE MENU_CODE = 1";

        //when
        Statement stmt1 = con.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        Menu menu1 = null;
        while(rset1.next()) {
            menu1 = new Menu();
            menu1.setMenuCode(rset1.getInt("MENU_CODE"));
            menu1.setMenuName(rset1.getString("MENU_NAME"));
        }

        Statement stmt2 = con.createStatement();
        ResultSet rset2 = stmt2.executeQuery(query);

        Menu menu2 = null;
        while(rset2.next()) {
            menu2 = new Menu();
            menu2.setMenuCode(rset2.getInt("MENU_CODE"));
            menu2.setMenuName(rset2.getString("MENU_NAME"));
        }

        //then
        Assertions.assertFalse(menu1 == menu2);
        Assertions.assertEquals(menu1.getMenuName(), menu2.getMenuName());
        // menu1과 menu2는 주소값이 다름 => 같은 db, 같은 데이터를 조회해왔는데 왜 주소값이 다른거야! 동일성을 보장하라!!
        // => JDA 탄생 배경

        rset1.close();
        stmt1.close();
        rset2.close();
        stmt2.close();
    }

}
