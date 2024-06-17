package com.dci.a3m.service;

import com.dci.a3m.entity.Member;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress = "coders-a3m@mailfence.com";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendResetPasswordEmail(Member member) {
        String to = member.getUser().getEmail();
        String subject = "Password Reset Request";
        String text = "To reset your password, please click the link below:\n"
                + "http://localhost:5000/reset-password?email=" + member.getUser().getEmail();

        sendEmail(to, subject, text);

    }

//    public void sendResetPasswordEmail(Admin admin) {
//        String to = admin.getUser().getEmail();
//        String subject = "Password Reset Request";
//        String text = "To reset your password, please click the link below:\n"
//                + "http://your-domain.com/reset-password?token=" + admin.getResetToken();
//        sendEmail(to, subject, text);
//    }
}
