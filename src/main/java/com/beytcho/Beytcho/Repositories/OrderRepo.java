package com.beytcho.Beytcho.Repositories;

import com.beytcho.Beytcho.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
