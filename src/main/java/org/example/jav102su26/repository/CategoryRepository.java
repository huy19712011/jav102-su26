package org.example.jav102su26.repository;

import jakarta.persistence.EntityManager;
import org.example.jav102su26.entity.Category;
import org.example.jav102su26.entity.Drink;
import org.example.jav102su26.utils.EntityManagerUtils;

import java.util.List;

public class CategoryRepository {

    public List<Category> getAll() {

        try (EntityManager em = EntityManagerUtils.getEntityManager()) {

            return em.createQuery("select c from Category c", Category.class).getResultList();
        }
    }
}
