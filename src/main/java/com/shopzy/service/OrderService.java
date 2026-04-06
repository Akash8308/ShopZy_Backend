package com.shopzy.service;

import com.shopzy.model.Order;
import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    List<Order> getOrdersByUser(Long userId);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}