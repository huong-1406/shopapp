package com.example.shopapp;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exceptions.DataNoteFoundException;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface IProductServicce {
     public Product createProduct(ProductDTO productDTO) throws Exception;

     Product getProductById(long id) throws Exception;

     Page<ProductResponse> getAllProducts(PageRequest pageRequest);

     Product updateProduct(long id ,ProductDTO productDTO) throws Exception;

     void deleteProduct(long id);

     boolean existByName(String name);

     ProductImage createProductImage(
             Long productId,
             ProductImageDTO productImageDTO) throws Exception;
}
