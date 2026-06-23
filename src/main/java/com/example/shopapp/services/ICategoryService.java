package com.example.shopapp.services;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    //thêm mới 1 category
    Category createCategory(CategoryDTO category);
    //lấy thông tin sản phẩm dựa tren id
    Category getCategoryById(Long id);
    //lâấy ra tất cả các categoryid
    List<Category> getAllCategories();
    //cập nhật 1 đối tươngj category nào đó từ id nó sẽ tìm trong db xem categỏy id nào có  bằng thế này
    Category updateCategory(long categoryId, CategoryDTO category);
    void deleteCategory(long id);

}
