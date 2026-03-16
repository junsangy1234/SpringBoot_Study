package jpabook.jpashop.dto;

import jpabook.jpashop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
