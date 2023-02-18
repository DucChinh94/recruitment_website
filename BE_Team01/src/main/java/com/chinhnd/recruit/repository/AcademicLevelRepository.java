package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.AcademicLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicLevelRepository extends JpaRepository<AcademicLevel, Long> {
}
