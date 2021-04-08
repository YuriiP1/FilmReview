package com.filmreview.springbootproject.service;

import com.filmreview.springbootproject.model.ConfirmationToken;
import com.filmreview.springbootproject.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }
}
