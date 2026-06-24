package com.example.shopapp.controllers;

import com.example.shopapp.IProductServicce;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import com.example.shopapp.dtos.ProductDTO;
import jakarta.validation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//restfull api
//tạo api
@RestController
//đường dẫn api
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductServicce productService;
    //api thêm dữ liệu
    @PostMapping(value="",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct( @Valid @ModelAttribute ProductDTO productDTO,
                                            //  @RequestPart("file") MultipartFile file,
                                            BindingResult result) {
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }//kiểm tra kích thước file định dạng
            Product newProduct = productService.createProduct(productDTO);
            //save the product
            List<MultipartFile> files = productDTO.getFiles();
            files = files ==null ? new ArrayList<MultipartFile>() : files;

            // Vòng lặp 1: Kiểm tra (Validate) tính hợp lệ của toàn bộ file trước
            for(MultipartFile file: files){
                if(file != null ){
                    if(file.getSize() ==0){
                        continue;
                    }

                    if(file.getSize()>10*1024*1024){//kích thước file>10MB

                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body("file is too large! Maximum size is 10MB");
                    }
                    String contentType = file.getContentType();
                    //kiểm tra xem file có phải file ảnh hay không
                    if(contentType == null || ! contentType.startsWith("image"))//nếu là file ảnh cần chứa chữ image
                    {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body("file must be an image");

                    }
                }
            }

            // Vòng lặp 2: Tiến hành lưu file thực sự khi tất cả đã hợp lệ
            for(MultipartFile file: files){
                if(file != null && file.getSize() > 0){
                    //Lưu filw và cập nhật thumbnail trong DTO
                    String filename = storeFile(file);
                    ProductImage productImage = productService.createProductImage(
                            newProduct.getId(),
                            ProductImageDTO.builder()
                                    .imageUrl(filename)
                                    .build());
                }
            }

            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //thêm UUID vào trước  tên file đó và đẩm bảo đây là tên duy nhâất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // đường dãn đến thư mục mà mình muốn lưu, tạo đường dâẫn tới thư mục upload
        //path là object đại diện cho file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        //kiểm tra thư mục có tồn tại không, nếu chưa có thì tạo mới cho nó
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
// đường dẫn đầy đủ đến filep
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //sao chép file vào thư mục đích,copy dữ liệu file upload
        //→ vào đường dẫn destination
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    //api lâys dữ liệu
    @GetMapping("")
    public ResponseEntity<String> getProducts(
            //lấy dữ liệu trên ủl vd:http://localhost:8080/users?id=1
            @RequestParam("page") int page,
            @RequestParam ("limit") int limit
    ) {
        return ResponseEntity.ok("getProducts here" );
    }
    // http://localhost:8080/api/v1/products/6
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with ID:"+productId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return ResponseEntity.ok(String.format("Product with ID: %d deleted successfully", id));
    }
//    {
//        "name":"ipad pro 2023",
//            "price": 812.34,
//            "thumbnail": "",
//            "description": "This is a test product",
//            "category_id": 1
//    }
}