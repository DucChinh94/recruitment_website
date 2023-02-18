package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.Profiles;
import com.chinhnd.recruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profiles,Long> {
    Profiles findByUser(User user);
}
