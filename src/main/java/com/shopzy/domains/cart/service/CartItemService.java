package com.shopzy.domains.cart.service;

import com.shopzy.domains.cart.model.CartItem;

import java.util.List;

public interface CartItemService {
    CartItem addItemToCart(CartItem cartItem);
    List<CartItem> getAllCartItems();
    List<CartItem> getCartItemsByUser(Long userId);
    CartItem updateCartItem(Long id, CartItem cartItem);
    void deleteCartItem(Long id);
}
