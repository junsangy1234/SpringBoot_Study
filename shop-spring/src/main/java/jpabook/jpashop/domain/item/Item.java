package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.category.CategoryItem;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    //OrderItem 을 OneToMany로 받아줄 필요가 없다.
    //Item이 OrderItem 상황을 알 필요가 없기 때문이다.
    private int price;
    private int stockQuantity;
    private String name;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantiy){
        int restStock = stockQuantity - quantiy;

        if (restStock < 0){
            throw new NotEnoughStockException("not enough stock");
        }

        this.stockQuantity = restStock;
    }

    protected void setItemBase(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void changePrice(int changePrice){
        if(changePrice < 0){
            throw new IllegalArgumentException("가격은 0원보다 작을수 없습니다.");
        }
        this.price = changePrice;
    }

    public void applyDiscount(int discountAmount) {
        int restPrice = this.price - discountAmount;
        if (restPrice < 0) {
            throw new IllegalStateException("할인 금액이 원래 가격보다 클 수 없습니다.");
        }
        this.price = restPrice;
    }
}
