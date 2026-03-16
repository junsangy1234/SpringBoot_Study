package jpabook.jpashop.domain.category;

import jakarta.persistence.*;
import jpabook.jpashop.domain.category.Category;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

@Entity
@Getter
public class CategoryItem {
    @Id @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
