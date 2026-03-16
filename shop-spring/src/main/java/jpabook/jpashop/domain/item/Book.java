package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@DiscriminatorValue("B")
@Getter
public class Book extends Item {
    private String author;
    private String isbn;

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        Book book = new Book();

        // 1. 공통 속성은 부모(Item)가 제공하는 메서드로 깔끔하게 세팅!
        book.setItemBase(name, price, stockQuantity);

        // 2. 책만의 고유 속성은 여기서 직접 세팅!
        book.author = author;
        book.isbn = isbn;

        return book;
    }
}
