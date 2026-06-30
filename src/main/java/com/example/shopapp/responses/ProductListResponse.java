package com.example.shopapp.responses;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
}
