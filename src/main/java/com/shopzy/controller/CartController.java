package com.shopzy.controller;

import com.shopzy.model.Cart;
import com.shopzy.model.CartItem;
import com.shopzy.service.impl.CartServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    public CartController(CartServiceImpl cartServiceImpl){
        this.cartServiceImpl = cartServiceImpl;
    }

    @PostMapping
    public Cart addItemToCart(@RequestBody Cart cartItem){
        return this.cartServiceImpl.createCart(cartItem);
    }
}