package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.category.CategoryItem;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class
Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    protected void setItemBase(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this. stockQuantity = stockQuantity;
    }

    public void addStock(int stock){
        this.stockQuantity += stock;
    }

    public void removeStock(int stock){
        int rest = this.stockQuantity - stock;

        if (rest < 0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }

        this.stockQuantity = rest;
    }

}
