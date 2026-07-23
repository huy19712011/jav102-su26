package org.example.jav102su26.service;

import lombok.RequiredArgsConstructor;
import org.example.jav102su26.entity.Category;
import org.example.jav102su26.repository.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getAll() {

        return categoryRepository.getAll();
    }
}
