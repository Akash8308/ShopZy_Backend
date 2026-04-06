package com.shopzy.service;

import com.shopzy.model.CartItem;
import java.util.List;

public interface CartItemService {
    CartItem addItemToCart(CartItem cartItem);
    List<CartItem> getAllCartItems();
    List<CartItem> getCartItemsByUser(Long userId);
    CartItem updateCartItem(Long id, CartItem cartItem);
    void deleteCartItem(Long id);
}