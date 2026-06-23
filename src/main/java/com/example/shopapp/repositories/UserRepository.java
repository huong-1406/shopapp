package com.example.shopapp.repositories;

import com.example.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPhoneNumber(String phoneNumber);// kiêmr tra người user có phonenumber có tồn tại hay không

    Optional<User> findByPhoneNumber(String phoneNumber);//truy vấn cơ sở dữ liệu mà không cânf đên câu lệnh select thủ công thay vì trả về null khi khong thấy nguoeif dùng nó sẽ ar về optinal
    //néu thấy chứa user thì sẽ trả về optinal chứa user, không thấy thì trả về optinal rỗng
//tự thưcj thi
}
