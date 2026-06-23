package com.example.shopapp.models;


import jakarta.persistence.*;
import lombok.*;

@Data //to string thay vì viết thủ công thì dùng to string để nó tự liệt kê

@Getter//lấy giá trị name
@Setter//gán giá trị name
@AllArgsConstructor//tạo constructor có đầy đủ tham số
@NoArgsConstructor//tạo constrtuctor không có tham sô
@Builder//
@Entity
@Table(name="categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)

    private String name;

}
