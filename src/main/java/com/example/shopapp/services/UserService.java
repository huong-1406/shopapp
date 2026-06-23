package com.example.shopapp.services;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exceptions.DataNoteFoundException;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNoteFoundException {
        String phoneNumber = userDTO.getPhoneNumber();//kiểm tra số điện thoại này đã tồn tại hay chưa nếu có rồi sẽ không cho đăng kí
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");

        };
        //convert from tu userDTO -> user
        //tạo ra 1 user mới
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(()
                        -> new DataNoteFoundException("Role not found"));
        newUser.setRole(role);
        //nếu có acccountid thì không cần mật khẩu ví dụ đăng nhập bằng facebook hoăc  google
        if(userDTO.getFacebookAccountId()== 0 && userDTO.getGoogleAccountId()== 0){
            String password = userDTO.getPassword();
            //*mã hoá mật khẩu sẽ nói trong spring security
            //String encodedPassword = passwordEncoder.encode(password);//nếu không đăng nhập bằng facebook hoăc google thì phải có mật khẩu
           // newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);// lưu user vào trong db
    }

    @Override
    public String Login(String phoneNumber, String password) {
        //liên quan nhiều đến security sẽ làm trong phần security
        return null;
    }
}
