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

    public static Movie createMovie(String name, int price, int stockQuantity, String director, String actor){
        Movie movie = new Movie();
        movie.setItemBase(name,price,stockQuantity);
        movie.director = director;
        movie.actor = actor;
        return movie;
    }
}
