package com.beytcho.Beytcho.Repositories;


import com.beytcho.Beytcho.Entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
