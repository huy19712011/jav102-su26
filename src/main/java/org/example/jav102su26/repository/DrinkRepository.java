package org.example.jav102su26.repository;

import jakarta.persistence.EntityManager;
import org.example.jav102su26.entity.Drink;
import org.example.jav102su26.utils.EntityManagerUtils;

import java.util.List;

public class DrinkRepository {

    public List<Drink> getAll() {

        try (EntityManager em = EntityManagerUtils.getEntityManager()) {

            return em.createQuery("select d from Drink d", Drink.class).getResultList();
        }
    }

    public Drink getById(int id) {

        try (EntityManager em = EntityManagerUtils.getEntityManager()) {

            return em.find(Drink.class, id);
        }
    }

    public void addDrink(Drink drink) {

        try (EntityManager em = EntityManagerUtils.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(drink);
            em.getTransaction().commit();
        }
    }

    public void updateDrink(Drink drink) {

        try (EntityManager em = EntityManagerUtils.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(drink);
            em.getTransaction().commit();
        }
    }
}
