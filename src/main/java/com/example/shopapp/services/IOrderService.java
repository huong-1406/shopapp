package com.example.shopapp.services;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO);

    OrderResponse getOrder(Long id);

    OrderResponse updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<OrderResponse> getAllOrders(Long userId);
}
