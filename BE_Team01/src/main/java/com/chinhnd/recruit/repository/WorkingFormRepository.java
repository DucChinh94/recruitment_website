package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.WorkingForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingFormRepository extends JpaRepository<WorkingForm, Long> {
}
