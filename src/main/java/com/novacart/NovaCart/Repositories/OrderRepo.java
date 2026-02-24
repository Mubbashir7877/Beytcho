package com.novacart.NovaCart.Repositories;

import com.novacart.NovaCart.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
