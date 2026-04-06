package com.shopzy.controller;

import com.shopzy.model.CartItem;
import com.shopzy.service.CartItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItem addItem(@RequestBody CartItem cartItem) {
        return cartItemService.addItemToCart(cartItem);
    }

    @GetMapping
    public List<CartItem> getAllItems() {
        return cartItemService.getAllCartItems();
    }

    @PutMapping("/{id}")
    public CartItem updateItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        return cartItemService.updateCartItem(id, cartItem);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return "Item removed from cart";
    }
}