package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        em.persist(item);
    }

    public Optional<Item> findOne(Long id){
        return Optional.ofNullable(em.find(Item.class, id));
    }

    public List<Item> findAll(){
        return em.createQuery("SELECT i FROM Item i",Item.class)
                .getResultList();
    }

}
