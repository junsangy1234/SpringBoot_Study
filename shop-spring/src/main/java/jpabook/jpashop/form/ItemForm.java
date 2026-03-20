package jpabook.jpashop.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {
    private Long id;

    private String name;
    private int stockQuantity;
    private int price;
    private String dtype;

    //book
    private String author;
    private String isbn;

    //album
    private String artist;
    private String etc;

    //movie
    private String director;
    private String actor;

}
