package com.shopzy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cart extends JpaRepository<Cart, Long> {
}
