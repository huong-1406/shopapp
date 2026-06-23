package com.example.shopapp.repositories;

import com.example.shopapp.models.Product;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//existsBy tảv về đối tượng boolen (false/true), truy ván sử ding
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);

    Page<Product> findAll(Pageable pageable);//phân trang
}
