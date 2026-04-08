package edu.miu.cs.cs489appsd.lab6.adsapp.repository;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
