package org.rome.hibernate.example.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rome.hibernate.example.entity.Order;
import org.rome.hibernate.example.entity.OrderItem;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class OrderRepository {

    private EntityManager entityManager;

    public Order create(List<OrderItem> orderItems, String name) {
        boolean isSuccess = true;
        Order order = null;
        try {
            entityManager.getTransaction().begin();
            order=  Order.builder().orderItemList(orderItems).name(name).build();
            entityManager.persist(order);
        } catch (Exception e) {
           isSuccess = false;
        } finally {
            if(isSuccess)
                entityManager.getTransaction().commit();
            else
                entityManager.getTransaction().rollback();
        }
        return order;
    }
}
