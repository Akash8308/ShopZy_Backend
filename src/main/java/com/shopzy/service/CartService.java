package com.shopzy.service;

import com.shopzy.model.Cart;

public interface CartService {
    Cart getCartByUser(Long userId);
    Cart createCart(Cart cart);
    void clearCart(Long cartId);
}