package com.shopzy.repository;

import com.shopzy.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartitemRepository extends JpaRepository<CartItem, Long> {
}
