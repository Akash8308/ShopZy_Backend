package com.shopzy.service.impl;

import com.shopzy.model.CartItem;
import com.shopzy.repository.CartItemRepository;
import com.shopzy.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem addItemToCart(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public List<CartItem> getCartItemsByUser(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public CartItem updateCartItem(Long id, CartItem cartItem) {
        CartItem existing = cartItemRepository.findById(id).orElseThrow();
        existing.setQuantity(cartItem.getQuantity());
        return cartItemRepository.save(existing);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}