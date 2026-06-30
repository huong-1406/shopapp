package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder//Nó là công cụ của thư viện Lombok, chỉ hỗ trợ cú pháp viết code Java nhanh hơn.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter//tạo constrtuctor không có tham sô
public class ProductDTO {
    @NotBlank(message = "title is required")
    @Size(min = 3, max = 200, message = "title must be between 3 and 100 characters")
    private String name;

    @Min(value = 0, message = "price must be greater than or equal to 0")
    @Max(value = 10000000, message = "price must be less than or equal to 10,000,000")
    private Float price;
    private String thumbnail;
    private String description;


    @JsonProperty("category_id")
    private Long categoryId;



}
