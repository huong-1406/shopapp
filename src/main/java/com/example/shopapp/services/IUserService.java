package com.example.shopapp.services;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exceptions.DataNoteFoundException;
import com.example.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNoteFoundException;// đăng kids tạo user mới giá trị tar về user sau khi insert vào db
    String Login(String phoneNumber, String password);//truyền vào số điện thoại và pass kiểm tả trong db xem có chưa nếu có nó sẽ trả về token mà user đã đăng nhập
}
