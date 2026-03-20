package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class  Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Embedded
    private Address address;

    public static Member createMember(String name, Address address){
        Member member = new Member();
        member.name = name;
        member.address = address;

        return member;
    }

    public void changeName(String name){
        if(name == null || name.trim().isBlank()){
            throw new IllegalArgumentException("이름은 필수 입니다.");
        }
        this.name = name;
    }
}
