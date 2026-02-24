package com.novacart.NovaCart.Repositories;


import com.novacart.NovaCart.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
