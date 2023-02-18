package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.StatusJobRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusJobRegisterRepository extends JpaRepository<StatusJobRegister, Long> {
    StatusJobRegister findStatusById(Long id);

}
