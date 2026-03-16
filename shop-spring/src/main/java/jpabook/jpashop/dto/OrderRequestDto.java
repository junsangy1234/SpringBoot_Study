package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;


//화면에 넘어오는 데이터를 받기위한 일회용 깡통 클래스
@Getter
@Setter
public class OrderRequestDto {
    private Long itemId;
    private int count;
}
