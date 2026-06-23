package com.example.shopapp.controllers;

import com.example.shopapp.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody//nhận diẹn dữ liệu
                                             @Valid OrderDTO orderDTO,//kích hoạt và lọc dữ liệu kiểm tra xem có đúng định dạng bên DTO không
                                         BindingResult result//tổng hợp lỗi và đưa xuống dưới kiểm tra điều kiện
    ) {
        //chia làm 2 tầng bộ lọc tầng 1 if(result.hasErrors()) chặn lỗi dữ liệu đâu vào
        // 1. Gom tất cả các câu thông báo lỗi (ví dụ: "Total money must be >=0", "phone number is required")
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                //đến đây dữu liệu hợp lệ thì trả về mã 200ok
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Create order successfully");
            // 2. Trả về mã lỗi 400 (Bad Request) kèm theo danh sách các câu thông báo lỗi ở trên
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{user_id}")//thêm biến đường dẫn "user_id"
    //GET http://localhost:8080/api/v1/orders/4
    public  ResponseEntity<?> getorders(
            @Valid
            @PathVariable("user_id") Long userId) {
        try {
            return ResponseEntity.ok("lấy danh sách  order từ user_id");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PutMapping("/{id}")
    //PUT http://locallhosst:8080/api/v1/orders/2
    //công việc của admin
    public  ResponseEntity<?> //"ResponseEntity là một lớp trong Spring Framework đại diện cho toàn bộ phản hồi HTTP (HTTP Response) gửi về cho Client. Nó cho phép chúng ta kiểm soát và cấu hình linh hoạt cả 3 thành phần: Trạng thái (Status Code), Tiêu đề (Headers), và Thân phản hồi (Body)."
    updateOrder(
            @Valid
            @PathVariable Long id,// trích xuất (lấy) giá trị id từ trên đường dẫn URL xuống biến id trong code Java.
            @RequestBody OrderDTO orderDTO// lấy toàn bộ dữ liệu người dùng gửi lên trong phần thân của yêu cầu (HTTP Request Body) và tự động chuyển nó thành một đối tượng Java.
            //: Nó lấy dữ liệu JSON do Frontend gửi lên để tạo ra một đối tượng (Object) rồi gán vào biến orderDTO
    ){
        return ResponseEntity.ok("cập nhật thông tin 1 order");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @Valid @PathVariable//ép oieeur ví dụ nguười dùng nhạp acv thì nó sẽ ba lỗi vì nó là kiểu long số

            Long id
    )
    {
        //xoá mềm =>cập nhật trường active= false nó thay vì xoas vĩnh viễn thì nó chỉ ẩn đi mà không mất lịch sử khi mà mình cần lại thì chỉ cần sửu false thành true là được
        return ResponseEntity.ok("Order deleted successfull");
    }
}
