package com.shopzy.controller;

import com.shopzy.model.CartItem;
import com.shopzy.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping
    public CartItem addItemToCart(@RequestBody CartItem cartItem){
        return this.cartService.addItemToCart(cartItem);
    }
}