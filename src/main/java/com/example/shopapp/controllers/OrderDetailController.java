package com.example.shopapp.controllers;
// các anottation
import com.example.shopapp.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController// là anotation căn bản troing spring boot dunGF ĐỂ XÂY DỰNG RESFULL API,
//MỌI DỮ LIỆU TẢ VỀ SẼ ĐỊNH DANGJ THÀNH jSON Nó đánh dấu class này là một Controller xử lý các yêu cầu HTTP. Mọi giá trị trả về từ các phương thức trong class này sẽ được tự động chuyển đổi thành định dạng (thường là JSON) và ghi trực tiếp vào phần thân (body) của phản hồi HTTP.
@RequestMapping("${api.prefix}/order_details")//Base URL
public class OrderDetailController {
    @PostMapping("")
    public  ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO newOrderDetail////kích hoạt và lọc dữ liệu kiểm tra xem có đúng định dạng bên DTO không
    ){
        return ResponseEntity.ok("Create order detail successfully");
    }
    @GetMapping("/{id}")
    public  ResponseEntity<?> getOrderDetail(
            @Valid
            @PathVariable("id") Long id){
        return ResponseEntity.ok("getorderdatil with id"+id);


    }//lấy ra danh sách orderdetail của 1 order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(
            @Valid
            @PathVariable("orderId") Long orderId
    ){
        return ResponseEntity.ok("getOrderDetails with orderId ="+orderId);
    }



    @PutMapping("/{id}")// method put dùng để update lại gioa hay thông tin đơn hàng nào đó
    public  ResponseEntity<?>updateOrderDetail(
            @Valid
            @PathVariable(("id")) Long id,
            @RequestBody OrderDetailDTO//id truyền vào sửa orderdetail nào
                    newOrderDetailData //thông tin mới cần cập nhật

    ){
        return ResponseEntity.ok("updateOrderDetail with id="+id
                +",newOrderDetailData: "+newOrderDetailData);


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(
            @Valid @PathVariable("id") Long id
            )
    {
        return ResponseEntity.noContent().build();
    }
}

