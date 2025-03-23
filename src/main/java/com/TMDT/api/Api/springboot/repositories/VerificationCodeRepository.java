package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    VerificationCode findByCode(String code);

    VerificationCode findByEmail(String email);

    VerificationCode findByEmailAndCode(String email, String code);

    void deleteByEmail(String email);
}
