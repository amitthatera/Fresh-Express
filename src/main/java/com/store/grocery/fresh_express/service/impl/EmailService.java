package com.store.grocery.fresh_express.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    private final String FROM;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine,
                        @Value("${spring.mail.username}") String FROM) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.FROM = FROM;
    }

    @Async
    public void sendAccountActivationEmail(String to, String username, String subject, String activationCode){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);

            Context context = new Context();
            context.setVariable("name",username);
            context.setVariable("activation_code", activationCode);

            String template = templateEngine.process("verify-account", context);
            mimeMessageHelper.setText(template,true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
           logger.error(e.getMessage());
        }
    }
}
