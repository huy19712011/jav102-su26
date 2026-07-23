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
}
