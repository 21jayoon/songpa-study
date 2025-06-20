package com.ohgiraffers.jpa02mapping.section01.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MemberRegistServiceTests {

    @Autowired
    private MemberRegistService memberRegistService;

    private static Stream<Arguments> getMember() {
        return Stream.of(
                Arguments.of(
                        "user01",
                        "pass01",
                        "홍길동",
                        "010-1234-5678",
                        "서울시 종로구",
                        LocalDateTime.now(),
                        "ROLE_MEMBER",
                        "Y"
                ),
                Arguments.of(
                        "user02",
                        "pass02",
                        "유관순",
                        "010-8765-4321",
                        "서울시 서초구",
                        LocalDateTime.now(),
                        "ROLE_ADMIN",
                        "Y"
                )
        );
    }
    @DisplayName("테이블 생성 테스트")
    @ParameterizedTest
    @MethodSource("getMember")
    void testCreateTable(
            String memberId, String memberPwd,
            String memberName, String phone, String address,
            LocalDateTime enrollDate, MemberRole memberRole, String status
    ) {
        //given
        MemberRegistDTO newMember = new MemberRegistDTO(
                memberId,
                memberPwd,
                memberName,
                phone,
                address,
                enrollDate,
                memberRole,
                status
        );
        //when

        //then
        Assertions.assertDoesNotThrow( //exception없이 정상적으로 수행되나요?
                () -> memberRegistService.registMember(newMember)
        );
    }

    @DisplayName("Test property access")
    @ParameterizedTest
    @MethodSource("getMember")
    void testAccessProperty(
            String memberId, String memberPwd,
            String memberName, String phone, String address,
            LocalDateTime enrollDate, MemberRole memberRole, String status
    ) {
        //1. Direct access(default) : approach to the field directly
        // -> difficult with verify etc. (another code neede)
        //2. property access : approach with property and getter method.
        // -> easier to verify etc., compared to direct accessing.
        // >> go to the Member class

        //given
        MemberRegistDTO newMember = new MemberRegistDTO(
                memberId,
                memberPwd,
                memberName,
                phone,
                address,
                enrollDate,
                memberRole,
                status
        );

        //when
        String registeredName = memberRegistService.registMemberAndFindName(newMember);

        //then
        assertEquals(memberName+"님", registeredName);
    }
}