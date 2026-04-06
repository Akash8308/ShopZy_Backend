package com.shopzy.service.impl;

import com.shopzy.model.Cart;
import com.shopzy.repository.CartRepository;
import com.shopzy.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}