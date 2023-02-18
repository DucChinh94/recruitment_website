package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticateRepository extends JpaRepository<User, Long> {

}
