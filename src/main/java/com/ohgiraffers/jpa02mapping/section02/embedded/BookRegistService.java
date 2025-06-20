package com.ohgiraffers.jpa02mapping.section02.embedded;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookRegistService {
    private BookRepository bookRepository;

    //생성자 주입으로 (Book)Repository에 의존성 주입
    public BookRegistService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    //DTO 타입 전달받아 엔티티로 가공한 후에 사용
    @Transactional
    public void registBook(BookRegistDTO newBook){
        Book book = new Book (
                newBook.getBookTitle(),
                newBook.getAuthor(),
                newBook.getPublisher(),
                newBook.getPublishedDate(),
                // DTO에 있는 regularPrice 필드 대신 별도의 Price class를 생성해서 넣어준다
                new Price(
                        newBook.getRegularPrice(),
                        newBook.getDiscountRate()
                )
        );
        bookRepository.save(book);
    }

}
