package org.rome.hibernate.example.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rome.hibernate.example.entity.Person;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class PersonRepository {

    private EntityManager entityManager;


    public Person create(String firstName, String lastName) {
        boolean isSuccessful = true;
        try {
            entityManager.getTransaction().begin();
            Person person = Person.builder().firstName(firstName).lastName(lastName).build();
            entityManager.persist(person);
            return person;
        } catch (Throwable e) {
           isSuccessful = false;
           throw new RuntimeException(e.getMessage(),e);
        } finally {
            if(isSuccessful)
                entityManager.getTransaction().commit();
            else
                entityManager.getTransaction().rollback();
        }
    }

    public List<Person> findAllPerson() {
        return entityManager.createQuery("SELECT p FROM Person p ").getResultList();
    }


}
