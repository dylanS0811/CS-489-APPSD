package edu.miu.cs.cs489appsd.lab6.adsapp.repository;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
