package com.ohgiraffers.jpa02mapping.section02.embedded;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_book")
public class Book {
    @Id
    @Column(name = "book_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookNo;
    @Column(name = "book_title")
    private String bookTitle;
    @Column(name = "author")
    private String author;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Embedded
    private Price price;
    // Price라는 클래스를 만들고 여러 entity에서 embedded(내장된) class로 활용하겠다

    protected Book() {
    }

    public Book(
            String bookTitle, String author, String publisher,
            LocalDate publishedDate, Price price
    ) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookNo=" + bookNo +
                ", bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishedDate=" + publishedDate +
                ", price=" + price +
                '}';
    }
}