package com.novacart.NovaCart.Repositories;

import com.novacart.NovaCart.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
