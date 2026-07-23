package org.example.jav102su26.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.jav102su26.entity.Category;
import org.example.jav102su26.entity.Drink;
import org.example.jav102su26.repository.CategoryRepository;
import org.example.jav102su26.repository.DrinkRepository;
import org.example.jav102su26.service.CategoryService;
import org.example.jav102su26.service.DrinkService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DrinkServlet", value = {
        "/drinks",
        "/drinks/add"
})
public class DrinkServlet extends HttpServlet {

    private DrinkService drinkService;
    private CategoryService categoryService;

    @Override
    public void init() {
        this.drinkService = new DrinkService(new DrinkRepository());
        this.categoryService = new CategoryService(new CategoryRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println(path);

        switch (path) {

            case "/drinks":
                listDrinks(request, response);
                break;
            case "/drinks/add":
                showDrinkForm(request, response);
                break;

        }
    }

    private void showDrinkForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Category> categories = categoryService.getAll();

        request.setAttribute("categories", categories);

        request.getRequestDispatcher("/views/drinkForm.jsp").forward(request, response);
    }

    private void listDrinks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Drink> drinks = drinkService.getAll();

        request.setAttribute("drinks", drinks);
        request.getRequestDispatcher("/views/drinkList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}