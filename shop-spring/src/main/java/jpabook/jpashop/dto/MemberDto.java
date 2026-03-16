package jpabook.jpashop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
