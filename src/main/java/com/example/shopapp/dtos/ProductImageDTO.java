package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder//Nó là công cụ của thư viện Lombok, chỉ hỗ trợ cú pháp viết code Java nhanh hơn.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImageDTO {

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be greater than 0")
    private Long productId;

    @Size( min =3 , max=200, message = "Image's name")
    @JsonProperty("image_url")//tên file ảnh mình vừa tạo ra
    private String imageUrl;

}
