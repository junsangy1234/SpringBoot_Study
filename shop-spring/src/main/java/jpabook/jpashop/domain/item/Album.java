package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("A")
@Getter
public class Album extends Item{
    private String artist;
    private String etc;

    public static Album createAlbum(String name, int price, int stockQuantity, String artist, String etc) {
        Album album = new Album();

        // 1. 공통 속성은 부모(Item)가 제공하는 메서드로 깔끔하게 세팅!
        album.setItemBase(name, price, stockQuantity);

        // 2. 책만의 고유 속성은 여기서 직접 세팅!
        album.artist = artist;
        album.etc = etc;

        return album;
    }
}
