package org.rome.hibernate.example.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rome.hibernate.example.entity.Product;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class ProductRepository {

    private EntityManager entityManager;

    public Product create(String name, BigDecimal value) {
        boolean isSuccess = true;
        try {
            entityManager.getTransaction().begin();
            Product product = Product.builder().name(name).price(value).build();
            entityManager.persist(product);
            return product;
        } catch (Exception e) {
            log.error(e.getMessage());
            isSuccess =false;
        } finally {
            if(isSuccess)
                entityManager.getTransaction().commit();
            else
                entityManager.getTransaction().rollback();
        }
        return null;
    }
}
