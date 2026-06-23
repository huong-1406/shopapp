package com.example.shopapp.repositories;

import com.example.shopapp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// findBy tạo truy vấn,xác định hướng cần tìm tỏng entity,map kết quả từ db trả về đô tượng java tương úng
//trả về đối tượng danh sách
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findByOrderId(long orderId);//tim nhung ban ghi co orderid=orderid do
}
