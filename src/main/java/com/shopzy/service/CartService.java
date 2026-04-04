package com.shopzy.service;

import com.shopzy.model.CartItem;
import com.shopzy.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    public CartItem addItemToCart(CartItem cartItem){
        return this.cartRepository.save(cartItem);
    }
}
