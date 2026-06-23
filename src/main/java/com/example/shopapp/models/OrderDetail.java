package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity//dùng để ánh xạ
@Table(name = "order_details")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    @Id//khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database tự đọng tăng giá trị thêm 1
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "price",nullable = false)
    private float price;


    @Column(name = "number_of_products",nullable = false)
    private int numberOfProducts;


    @Column(name = "total_money",nullable = false)
    private int totalMoney;

    @Column(name = "color")
    private String color;




}
