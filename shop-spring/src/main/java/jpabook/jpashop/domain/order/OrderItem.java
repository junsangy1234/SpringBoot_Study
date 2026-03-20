package jpabook.jpashop.domain.order;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

@Entity
@Getter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderitem = new OrderItem();
        orderitem.item = item;
        orderitem.orderPrice = orderPrice;
        orderitem.count = count;

        orderitem.getItem().removeStock(count);

        return orderitem;
    }

    protected void setOrder(Order order){
        this.order = order;
    }

    protected void cancel(){
        this.getItem().addStock(count);
    }

    public int getTotalPrice(){
        return orderPrice * count;
    }

}
