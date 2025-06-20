package com.ohgiraffers.jpa02mapping.section02.embedded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
public class BookRegistServiceTests {
    // 테스트 클래스는 보통 필드 주입(@Autowired)으로도 충분합니다.
    //이 클래스는 test 코드라서 의존성 주입을 받을 필욘 없지만...
    // JPA 수업의 일관성을 위해 테스트 코드에서도 생성자 주입을 적용
    // 생성자 주입을 사용하면, 불변성(immutable)을 보장할 수 있고, 의존성 주입이 누락되는 것을 컴파일 타임에 체크할 수 있어 더 안전
    //생성자 주입 방식은 필드 주입보다 테스트 작성 시 의존성을 명확하게 드러낼 수 있다.
    @Autowired
    private BookRegistService bookRegistService;

    private static Stream<Arguments> getBook() { // argument를 받았다
        return Stream.of(
                Arguments.of(
                        "자바 ORM 표준 JPA 프로그래밍",
                        "김영한",
                        "에이콘",
                        LocalDate.now(),
                        43000,
                        0.1
                )
        );
    }
    @ParameterizedTest
    @MethodSource("getBook")
    void testCreateEmbeddedPriceOfBook(
            String bookTitle, String author, String publisher,
            LocalDate publishedDate, int regularPrice, double discountRate
    ) {
        //given
        //전달받은 argument를 갖고 온다
        BookRegistDTO newBook = new BookRegistDTO(
                bookTitle,
                author,
                publisher,
                publishedDate,
                regularPrice,
                discountRate
        );
        //when
        //then
        Assertions.assertDoesNotThrow(
                () -> bookRegistService.registBook(newBook)
        );
    }
}
