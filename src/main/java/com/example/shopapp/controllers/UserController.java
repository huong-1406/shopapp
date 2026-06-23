package com.example.shopapp.controllers;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shopapp.dtos.*;

import java.util.List;

@RestController

@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
//register dùng trong phương thức post để thực hiện chức năng đag kí tài khoản người dùng
    @PostMapping("/register")
    //tên hàm tạo là createUser
    //@Valid kiểm tra dữ liệu người dùng xem ôs đúng định dạng không
    //@RequestBody UserDTO userDTO lấy đữ liệu người dùng từ forrmđăng kí để nhét vào đối tượng UserDTO xử lí
    //luồng try cath khối try nếu khong trùng username và pass thì hệ thống trả về mã 200 ok và kèm user tạo thành công
    //khối cath nếu xảy ra lỗi thì tr về mã 400 BAD REQUEST kèm e.getMessage() hiẻn thị cho người dùng biêt lỗi ở đâu

    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO,
                                        BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password does not match");

            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Register successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //@PostMapping("/login"): Tiếp nhận một yêu cầu POST gửi tới địa chỉ /login. Khác với Register (tạo mới), phương thức POST ở đây được dùng để gửi thông tin bảo mật (mật khẩu) ẩn trong Request Body.
    //@Valid: Kiểm tra xem dữ liệu thô người dùng nhập vào có hợp lệ về mặt định dạng không (Ví dụ: kiểm tra xem trường username hoặc password có bị để trống hay không trước khi tốn hiệu năng xử lý tiếp).
   // @RequestBody UserLoginDTO userLoginDTO: Gom dữ liệu đăng nhập (thường chỉ gồm username/email và password) từ Client gửi lên và gán vào đối tượng userLoginDTO
    //Token LÀ một chuỗi chữ viết liền, nhưng bên trong chứa sẵn Thông tin của bạn + Thời gian hết hạn + Chữ ký chống giả.
    //
    //Nó giúp Server nhận diện được bạn là ai mà không cần phải bắt bạn nhập lại mật khẩu, cũng không cần Server phải tốn bộ nhớ lưu trữ trạng thái đăng nhập của bạn.
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO){
        //kiểm tra thông tin đăng nhập và sinh token
        String token = userService.Login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        //trả vè token trong response
        return ResponseEntity.ok(token);
    }
}


