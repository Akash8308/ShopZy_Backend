package com.shopzy.domains.cart.service;

import com.shopzy.domains.cart.model.Cart;

public interface CartService {
    Cart getCartByUser(Long userId);
    Cart createCart(Cart cart);
    void clearCart(Long cartId);
}
