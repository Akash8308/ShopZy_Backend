package com.shopzy.domains.cart.service.impl;

import com.shopzy.domains.cart.model.Cart;
import com.shopzy.domains.cart.repository.CartRepository;
import com.shopzy.domains.cart.service.CartService;
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
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
