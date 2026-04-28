package com.example.shopapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
//hiện tất cả các category
public class CategoryController {

    @GetMapping("") //http://localhost:8080/api/v1/categories? page=1& limit=10
    public ResponseEntity<String> getAllCategories(
            @RequestParam ("page") int page,
            @RequestParam ("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("Page: %d, Limit: %d", page, limit) );
    }

    @PostMapping("")
    public ResponseEntity<String> insertCategory() {
        return ResponseEntity.ok("This in insertCategory");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id) {
        return ResponseEntity.ok("insertCategory With " + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        return ResponseEntity.ok("deleteCategory With " + id);
    }

}