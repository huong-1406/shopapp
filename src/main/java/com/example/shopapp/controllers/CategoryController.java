package com.example.shopapp.controllers;

import com.example.shopapp.dtos.*;
import com.example.shopapp.models.Category;
import com.example.shopapp.services.CategoryService;
import com.example.shopapp.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
//hiện tất cả các category
//Dependency Injection
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result) {
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Insert category successfully");
    }
    //hiện tất cả các category
    @GetMapping("") //http://localhost:8080/api/v1/categories? page=1& limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam ("page") int page,
            @RequestParam ("limit") int limit
    ) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
     // nêu tham số truyền vào là 1 object thì =>data trasfer= request object


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable long id,
            @Valid
            @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update category successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("deleteCategory With  id :" + id + " successfully");
    }

}