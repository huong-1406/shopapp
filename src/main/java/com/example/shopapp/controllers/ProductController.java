package com.example.shopapp.controllers;

import com.example.shopapp.IProductServicce;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.responses.ProductListResponse;
import com.example.shopapp.responses.ProductResponse;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.Objects;
import java.util.UUID;
//restfull api
//tạo api

@RestController
//đường dẫn api
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductServicce productService;
    private final ProductRepository productRepository;

    //api thêm dữ liệu
    @PostMapping("")
    public ResponseEntity<?> createProduct( @Valid @RequestBody ProductDTO productDTO,
                                           //
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
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping(value="uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable ("id") long productId,
            @ModelAttribute("file") List<MultipartFile> files
    ){

        //save the product
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files ==null ? new ArrayList<MultipartFile>() : files;
            if(files.size() >  ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
                return ResponseEntity.badRequest().body("you can only upload maximum 5 images");
            }
            List<ProductImage> productImages = new ArrayList<>();
            // Vòng lặp 1: Kiểm tra (Validate) tính hợp lệ của toàn bộ file trước
            for(MultipartFile file: files){
                    if (file.getSize() == 0) {
                        continue;
                    }

                    if (file.getSize() > 10 * 1024 * 1024) {//kích thước file>10MB

                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body("file is too large! Maximum size is 10MB");
                    }
                    String contentType = file.getContentType();
                    //kiểm tra xem file có phải file ảnh hay không
                    if (contentType == null || !contentType.startsWith("image"))//nếu là file ảnh cần chứa chữ image
                    {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body("file must be an image");

                    }

                    String filename = storeFile(file);
                    ProductImage productImage = productService.createProductImage(
                            existingProduct.getId(),
                            ProductImageDTO.builder()
                                    .imageUrl(filename)
                                    .build()
                    );
                    productImages.add(productImage);

                }


            return ResponseEntity.ok(productImages);
                 } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }








    private String storeFile(MultipartFile file) throws IOException {
        if( ! isImageFile(file) || file.getOriginalFilename() == null){
            throw new IOException("Invalid image file format");

        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));


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



     private  boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    //api lâys dữ liệu
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            //lấy dữ liệu trên ủl vd:http://localhost:8080/users?id=1
            @RequestParam("page") int page,
            @RequestParam ("limit") int limit
    ) {
        //tạo page request ta thông tin trang giới hạn
        PageRequest pageRequest = PageRequest.of(//truyền vào và săp xếp theo thứ tự giảm dần sản phẩm nào mới nhất nó sẽ chuyển lên trên dầu
                page, limit,
                Sort.by("createdAt").descending()) ;
        Page<ProductResponse> productPage = productService.getAllProducts( pageRequest);// cho giới hạn mỗi lần chỉ đưojc lấy limit bản ghi thôi tránh trường hợp lấy db lên quá nhiều
        //lấy ra tônggr số trang
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPages)
                .build() );

    }
    // http://localhost:8080/api/v1/products/6
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @PathVariable("id") Long productId) {
        try {
           Product exitingProduct= productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(exitingProduct));
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product with ID: %d deleted successfully", id));


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //@PostMapping("/generateFakeProducts")
    private ResponseEntity<String> generateFakeProducts() {
        Faker faker = new Faker();
        for(int i=0 ; i<1000000; i++){
            String productName = faker.commerce().productName();
            if (productRepository.existsByName(productName)) {
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(10,90000000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long)faker.number().numberBetween(2,9))
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }
        return ResponseEntity.ok("Fake products created successfully");
    }
@PutMapping("/{id}")
public ResponseEntity<?> updateProduct(

        @PathVariable long id,
        @RequestBody ProductDTO productDTO
)
{
    try {
        Product updateProduct = productService.updateProduct(id,productDTO);
        return ResponseEntity.ok(updateProduct);

    } catch(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    }
}

}