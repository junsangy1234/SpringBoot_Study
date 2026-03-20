package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String dtype; // BOOK, ALBUM, MOVIE 구분용

    // 자식들 전용 필드를 몽땅 모아둡니다
    private String author;
    private String isbn;
    private String artist;
    private String etc;
    private String actor;
    private String director;
}
