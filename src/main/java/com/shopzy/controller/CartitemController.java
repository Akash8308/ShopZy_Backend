package com.shopzy.controller;

import com.shopzy.model.CartItem;
import com.shopzy.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartitemController {

    private final CartService cartService;

    public CartitemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartItem addItem(@RequestBody CartItem cartItem) {
        return cartService.addItemToCart(cartItem);
    }

    @GetMapping
    public List<CartItem> getAllItems() {
        return cartService.getAllCartItems();
    }

    @PutMapping("/{id}")
    public CartItem updateItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        return cartService.updateCartItem(id, cartItem);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return "Item removed from cart";
    }
}