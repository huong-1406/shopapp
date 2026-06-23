package com.example.shopapp.dtos;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;//g

@Data
@Getter//lấy giá trị name
@Setter//gán giá trị name
@AllArgsConstructor//tạo constructor có đầy đủ tham số
@NoArgsConstructor//tạo constrtuctor không có tham sô

public class CategoryDTO {
    @NotEmpty( message ="category's name can not be empty")
    private String name ;
}
