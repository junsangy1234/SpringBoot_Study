package jpabook.jpashop.repository.order.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class OrderItemQueryDto {
    private Long orderId; // 주문 번호 (나중에 묶을 때 필요함)
    private String itemName;
    private int orderPrice;
    private int count;
}