package com.example.shopapp;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exceptions.DataNoteFoundException;
import com.example.shopapp.exceptions.InvalidParamException;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService implements IProductServicce{
    private final ProductRepository productRepository;//final bawts buoocj phair tham chiees laanf 1 vaf 1 laanf
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNoteFoundException {
        Category existingCategory = categoryRepository// kiểm soát dầu vào ví dụ dữ liệu ngời dùng đẩy lên
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNoteFoundException(
                                "Cannot find category with id: " +productDTO.getCategoryId() ));
        Product newProduct = Product.builder()
                //ánh xạ mọituwf bên DTo sang
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);

    }

    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNoteFoundException(
                        "Cannot find product with id: " + productId));
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        //lấy  danh sách sản phẩm theo trang(page) và giới hạn(limit):tổng số sản phẩm bên trong 1 trang
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(
            long id, ProductDTO productDTO
    )
            throws Exception {
        Product existingProduct = getProductById(id);//lấy ra rồi thì kiểm tra xem có chưa
        if(existingProduct != null){
            //copy các thuộc tính từ DTO sang product
            //có thể sử dụng trong modelmapper
            Category existingCategory = categoryRepository// kiểm soát dầu vào ví dụ dữ liệu ngời dùng đẩy lên
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNoteFoundException(
                                    "Cannot find category with id: " +productDTO.getCategoryId() ));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setDescription(productDTO.getDescription());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);//kết quả xao snđi sẽ không còn nữa
        //nếu tồn tại thì thự hiện xoá
        //khi tồn tại thì sẽ lôi ra xoá được
        optionalProduct.ifPresent(productRepository::delete);


    }

    @Override
    public boolean existByName(String name) {
        return productRepository.existsByName(name);
    }
    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception//them 1 ảnh vào sản phẩm này
    {
        Product existingProduct = productRepository// kiểm soát dầu vào ví dụ dữ liệu ngời dùng đẩy lên
                .findById(productImageDTO.getProductId())
                .orElseThrow(() ->
                        new DataNoteFoundException(
                                "Cannot find product with id: " +productImageDTO.getProductId() ));
        //ProductImageRepository để lưu dối tượng productimage mới vào

        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //không cho insert quá 56 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >=5){
            throw new InvalidParamException("Number of images must be <=5");
        }
        return productImageRepository.save(newProductImage);
    }
}
