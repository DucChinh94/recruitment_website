package com.chinhnd.recruit.repository;

import com.chinhnd.recruit.entity.OTP;
import com.chinhnd.recruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    OTP findByCode(String code);
    Optional<User> findOTPByUser(Long otp);

}
