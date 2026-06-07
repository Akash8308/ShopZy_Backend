package com.shopzy.domains.catalog.repository;

import com.shopzy.domains.catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
