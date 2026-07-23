package org.example.jav102su26.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.jav102su26.entity.Drink;
import org.example.jav102su26.repository.CategoryRepository;
import org.example.jav102su26.repository.DrinkRepository;

import java.util.List;

@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public List<Drink> getAll() {

        return drinkRepository.getAll();
    }
}
