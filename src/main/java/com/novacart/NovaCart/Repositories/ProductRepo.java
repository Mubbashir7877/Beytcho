package com.novacart.NovaCart.Repositories;

import com.novacart.NovaCart.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long category);

    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);



}
