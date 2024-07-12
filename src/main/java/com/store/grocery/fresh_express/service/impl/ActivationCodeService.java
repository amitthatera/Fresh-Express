package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.custom_exception.ExpiredActivationCodeException;
import com.store.grocery.fresh_express.model.ActivationCode;
import com.store.grocery.fresh_express.model.User;
import com.store.grocery.fresh_express.repository.ActivationCodeRepository;
import com.store.grocery.fresh_express.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class ActivationCodeService {

    private final EmailService emailService;

    private final ActivationCodeRepository codeRepository;

    private final UserRepository userRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ActivationCodeService.class);

    public ActivationCodeService(EmailService emailService, ActivationCodeRepository codeRepository,
                                 UserRepository userRepository) {
        this.emailService = emailService;
        this.codeRepository = codeRepository;
        this.userRepository = userRepository;
    }

    public void activateAccount(String token) {
        ActivationCode savedToken = codeRepository.findByToken(token)
                .orElseThrow(() -> new ExpiredActivationCodeException("Invalid token"));
        if (savedToken.getUser() != null && !savedToken.getUser().isEnabled()) {
            if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
                sendValidationEmail(savedToken.getUser());
                throw new ExpiredActivationCodeException("Activation token has expired. A new token has been send to the same email address");
            }
            User user = userRepository.findById(savedToken.getUser().getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found!!"));
            user.setEnabled(true);
            user.setEmailVerified(true);
            userRepository.save(user);

            savedToken.setValidatedAt(LocalDateTime.now());
            codeRepository.saveAndFlush(savedToken);
        }
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode();
        var token = ActivationCode.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();
        codeRepository.saveAndFlush(token);
        return generatedToken;
    }

    public void sendValidationEmail(User user) {
        LOGGER.info("Sending validation email to user: {}", user.getId());

        String newToken = generateAndSaveActivationToken(user);
        String fullName = user.getFirstName() + " " + user.getLastName();

        emailService.sendAccountActivationEmail(
                user.getEmailAddress(),
                fullName,
                "New User Account Verification",
                newToken);

    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

}
