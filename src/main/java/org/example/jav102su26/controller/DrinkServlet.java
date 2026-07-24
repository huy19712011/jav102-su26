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

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "DrinkServlet", value = {
        "/drinks",
        "/drinks/add"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB
        maxFileSize = 5 * 1024 * 1024,        // 5 MB
        maxRequestSize = 10 * 1024 * 1024     // 10 MB
)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println(path);

        switch (path) {

            case "/drinks/add":
                addDrink(request, response);
                break;

        }
    }

    private void addDrink(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Drink drink = getDrinkFromForm(request);

        drinkService.addDrink(drink);

        response.sendRedirect(request.getContextPath() + "/drinks");

    }

    private Drink getDrinkFromForm(HttpServletRequest request) throws ServletException, IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = saveUploadedImage(request);
        String priceParam = request.getParameter("price");
        String activeParam = request.getParameter("active");

        long categoryId = Long.parseLong(request.getParameter("categoryId"));
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Invalid category id: " + categoryId);
        }
        Drink drink = new Drink();
        String idParam = request.getParameter("drinkId");
        if (idParam != null && !idParam.isBlank()) {
            drink.setId(Integer.parseInt(idParam));
        }
        drink.setName(name);
        drink.setDescription(description);
        drink.setImage(image);
        drink.setCategory(category);
        if (priceParam != null && !priceParam.isBlank()) {
            drink.setPrice(new BigDecimal(priceParam));
        }
        drink.setActive(activeParam != null);
        return drink;
    }

    // Saves the uploaded "image" part under the webapp's /uploads directory and
    // returns the generated file name to store on the drink. Returns null when
    // no file was submitted (e.g. an edit that keeps the existing image).
    private String saveUploadedImage(HttpServletRequest request) throws ServletException, IOException {

        Part imagePart = request.getPart("image");
        if (imagePart == null || imagePart.getSize() == 0) {
            return null;
        }

        String submitted = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        int dot = submitted.lastIndexOf('.');
        String ext = dot >= 0 ? submitted.substring(dot) : "";
        String fileName = UUID.randomUUID() + ext;

        String uploadDir = getServletContext().getRealPath("/uploads");
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        imagePart.write(uploadDir + File.separator + fileName);
        return fileName;
    }
}