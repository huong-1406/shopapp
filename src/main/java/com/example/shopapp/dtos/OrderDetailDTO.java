package com.example.shopapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder//Nó là công cụ của thư viện Lombok, chỉ hỗ trợ cú pháp viết code Java nhanh hơn.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {

    //khi dùng Json thì nếu muốn sửa code thì chỉ cần sửa ở Jso còn không cần sửa ở cod elogic
    @JsonProperty("order_id")//dùng khi biến cso 2 ten ví dụ thêm id và at
    @Min(value = 1, message = "Order id must be > 0")
    private Long orderId;

    @JsonProperty("product_id")//dùng khi biến cso 2 ten ví dụ thêm id và at
    @Min(value = 0, message = "Order id must be >= 0")
    private Long productId;

    private Long price;

    @JsonProperty("number_of_product")
    @Min(value = 1, message = "number_of_product id must be > 0")
    private int numberOfProduct;

    @JsonProperty("total_money")
    @Min(value = 0, message = "total_money id must be >= 0")
    private int totalMoney;

    private String color;
}


