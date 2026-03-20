package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn){
        Book book = new Book();
        book.setItemBase(name,price,stockQuantity);
        book.author = author;
        book.isbn = isbn;
        return book;
    }
}
