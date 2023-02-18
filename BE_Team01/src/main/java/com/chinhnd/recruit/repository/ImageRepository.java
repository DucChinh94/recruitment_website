package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUserId(Long id);
}
