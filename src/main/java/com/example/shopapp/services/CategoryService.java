package com.example.shopapp.services;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;
import com.example.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
@RequiredArgsConstructor//của lombok


public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;//hàm khởi tạo
//    public CategoryService(CategoryRepository categoryRepository)//index vào, khi nào chạy qua đây sẽ tự khởi tạo ở đâu đó và lấy đối tượng category cho hàm khởi tạo
    @Override

    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .build();//tạo ra 1 đối tượng rỗng xong khởi tạo từng thành phần
        return categoryRepository.save(newCategory);
    }
//lấy ra
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));//cách 2.orElse(null);// tìm ra category có id truyền vào như trên nếu không thấy trả về null

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();//
    }

    @Override
    public Category updateCategory(long categoryId,
                                   CategoryDTO categoryDTO) {//update lại thông tin của category trừ khoá chính là id

        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory ;
    }

    @Override
    public void deleteCategory(long id) {
        //xoa sứng tức xoá xong mất luôn
        //xoá mềm cập nhật trường activve =false
        categoryRepository.deleteById(id);


    }
}
