package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("M")
@Getter
public class Movie extends Item{
    private String director;
    private String actor;

    public static Movie createMovie(String name, int price, int stockQuantity, String director, String actor) {
        Movie movie = new Movie();

        // 1. 공통 속성은 부모(Item)가 제공하는 메서드로 깔끔하게 세팅!
        movie.setItemBase(name, price, stockQuantity);

        // 2. 책만의 고유 속성은 여기서 직접 세팅!
        movie.director = director;
        movie.actor = actor;


        return movie;
    }
}
