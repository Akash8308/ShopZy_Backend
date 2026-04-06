package com.shopzy.controller;

import com.shopzy.model.CartItem;
import com.shopzy.service.impl.CartServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartitemController {

    private final CartServiceImpl cartServiceImpl;

    public CartitemController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }

    @PostMapping
    public CartItem addItem(@RequestBody CartItem cartItem) {
        return cartServiceImpl.addItemToCart(cartItem);
    }

    @GetMapping
    public List<CartItem> getAllItems() {
        return cartServiceImpl.getAllCartItems();
    }

    @PutMapping("/{id}")
    public CartItem updateItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        return cartServiceImpl.updateCartItem(id, cartItem);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        cartServiceImpl.deleteCartItem(id);
        return "Item removed from cart";
    }
}