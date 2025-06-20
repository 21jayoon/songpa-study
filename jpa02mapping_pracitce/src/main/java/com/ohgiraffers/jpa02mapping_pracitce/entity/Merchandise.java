package com.ohgiraffers.jpa02mapping_pracitce.entity;

import jakarta.persistence.*;

import java.util.Date;

/*@Entity 어노테이션은 JPA에서 사용되는 엔티티 클래스임을 표시한다.
이 어노테이션을 사용하면 해당 클래스가 DB의 테이블과 매핑된다.
@Entity 어노테이션은 클래스 선언 위에 위치해야 한다.
name 속성을 사용하여 엔티티 클래스와 매핑될 테이블의 이름을 지정할 수도 있는데,
생략하면 자동으로 클래스 이름을 엔티티명으로 사용한다.
프로젝트 내에 다른 패키지에도 동일한 엔티티가 존재하는 경우
해당 엔티티를 식별하기 위한 중복 되지 않는 name을 지정해주어야 한다.

기본 생성자는 필수로 작성해야 한다. -> class 구현체 {} 내부에 여러 필드 중
PK가 될 필드를 @Id로 지정
final 클래스, enum, interface, inner class 에서는 사용할 수 없다.
저장할 필드에 final을 사용하면 안된다.
*/
@Entity(name = "entityMerchandise")
@Table(name = "tbl_merch")
public class Merchandise {
    /*Primary key 에는 @Id 어노테이션과 @GeneratedValue 어노테이션을 사용한다.
     @Id 어노테이션은 엔티티 클래스에서 primary key 역할을 하는 필드를 지정할 때 사용된다.
     @GeneratedValue 어노테이션을 함께 사용하면 primary key 값을 자동으로 생성할 수 있다.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merch_no")
    private int merchNo;

    /*데이터베이스마다 기본 키를 생성하는 방식이 서로 다르다.
    @GeneratedValue 어노테이션은 다음과 같은 속성을 갖고 있다.
    strategy : 자동 생성 전략을 지정
      **GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임(MySQL의 AUTO_INCREMENT)
        GenerationType.SEQUENCE : 데이터베이스 시퀀스 객체 사용(ORACLE의 SEQUENCE)
        GenerationType.TABLE : 키 생성 테이블 사용
        GenerationType.AUTO : 자동 선택 (MySQL이라면 IDENTITY, ORACLE이라면 SEQUENCE로 선택)
    generator : strategy 값을 GenerationType.TABLE로 지정한 경우 사용되는 테이블 이름을 지정
    initialValue : strategy 값을 GenerationType.SEQUENCE로 지정한 경우 시퀀스 초기값을 지정
    allocationSize : strategy 값을 GenerationType.SEQUENCE로 지정한 경우 시퀀스 증가치를 지정
    */
    /*  merchNo에 @GeneratedValue(strategy = GenerationType.IDENTITY)를 넣어줬기 때문에
    자동으로 MySQl에서 AUTO_INCREMENT를 적용시킨 것과 같아져 UNIQUE 속성이 적용됨
     */

    @Column(
            name = "merch_name", unique = true,
            nullable = false, columnDefinition = "varchar(10)"
    )
    private String memberId;

    @Column(
            name = "merch_type", nullable = false,
            columnDefinition = "varchar(10)"
    )
    private String merchType;
    //merchType에는 공책, 펜, 3색펜, 자석, 키링 등의 MD 종류가 속성으로 들어간다.
    // 따라서 column 속성에공백이 불가능하단 뜻인 not null(nullable=false)과
    // 문자열 전체 길이에 관한 제한 속성 columnDefinition=varchar(10)을 넣어줬음

    @Column(
            name = "merch_name", unique = true,
            nullable = false, columnDefinition = "varchar(30)"
    )
    private String merchName;
    //merchName에는 각 MD의 이름이 들어간다. (예시. "'최고의 추리소설'공책", 셜록이 쓰던 만년필 등)
    // MD를 판매한다고 가정할 때, 상품명이 같거나 없으면 안 되기 때문에
    // column 속성에 공백이 불가능하단 뜻인 not null(nullable=false)과
    // 같은 값 중복을 불허하는 unique=true,
    // 문자열 전체 길이에 관한 제한 속성 columnDefinition=varchar(30)을 넣어줬음

    @Column(
            name = "merch_price", nullable = false
    )
    private int merchPrice;
    //merchPrice에는 공백이 있으면 안 되기 때문에 not null(nullable=false)를 넣어줌

    @Column(name = "send_abroad_available")
    private boolean sendAbroadAvailable;

    @Column(name = "enroll_date")
    private Date enrollDate;

    /*@Column 어노테이션은 엔티티 클래스의 필드와 데이터베이스의 컬럼 매핑을 지정한다.
     이 어노테이션을 사용하면 필드의 이름과 데이터베이스의 컬럼명, 자료형 등을 지정할 수 있다.
     */

}
