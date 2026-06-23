package com.example.shopapp.repositories;

import com.example.shopapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Repository là nơi chứa các hàm thêm mới cập nhật user product để thao tác đến model và từ model đến cơ sở dữ liệu

public interface CategoryRepository  extends JpaRepository<Category,Long>{



}
