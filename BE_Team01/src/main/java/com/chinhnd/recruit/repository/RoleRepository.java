package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByCode(String code);
}
