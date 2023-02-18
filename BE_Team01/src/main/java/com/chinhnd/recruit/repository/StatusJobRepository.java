package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.StatusJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusJobRepository extends JpaRepository<StatusJob, Long> {
    StatusJob findStatusById(Long id);
}
