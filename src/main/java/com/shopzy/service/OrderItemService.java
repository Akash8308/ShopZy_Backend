package com.shopzy.service;

import com.shopzy.model.OrderItem;
import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> getAllOrderItems();
    OrderItem getOrderItemById(Long id);
    List<OrderItem> getItemsByOrder(Long orderId);
    OrderItem updateOrderItem(Long id, OrderItem orderItem);
    void deleteOrderItem(Long id);
}