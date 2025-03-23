package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.VerificationCode;
import com.TMDT.api.Api.springboot.repositories.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerificationCodeService {
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public void save(VerificationCode verificationCode) {
        verificationCodeRepository.save(verificationCode);
    }

    public boolean isExist(String email, String code) {
        return verificationCodeRepository.findByEmailAndCode(email, code).getExpirationTime().isAfter(LocalDateTime.now());
    }

}
