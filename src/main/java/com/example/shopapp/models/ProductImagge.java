package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity//dùng để ánh xạ
@Table(name = "product_images")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImagge {

    @Id//khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database tự đọng tăng giá trị thêm 1
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Category product;

    @Column(name = "image_url",length = 300)
    private String imageUrl;
}
