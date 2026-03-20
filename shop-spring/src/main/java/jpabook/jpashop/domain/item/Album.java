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

    public static Album createAlbum(String name, int price, int stockQuantity, String artist, String etc){
        Album album = new Album();
        album.setItemBase(name,price,stockQuantity);
        album.artist = artist;
        album.etc = etc;
        return album;
    }
}
