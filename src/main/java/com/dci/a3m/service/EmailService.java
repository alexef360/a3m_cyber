package com.dci.a3m.service;

import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Admin;
import com.dci.a3m.entity.Token;
import com.dci.a3m.entity.User;
import com.dci.a3m.repository.TokenRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress = "coders-a3m@mailfence.com";
    private final TokenService tokenService;
    private final UserService userService;
    private final MemberService memberService;
    private final AdminService adminService;

    public EmailService(JavaMailSender mailSender, TokenService tokenService, UserService userService, MemberService memberService, AdminService adminService) {
        this.mailSender = mailSender;
        this.tokenService = tokenService;
        this.userService = userService;
        this.memberService = memberService;
        this.adminService = adminService;
    }

    // Send email method
    public void sendEmail(String to, String subject, String text) {
        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // Send reset password email for member
    public void sendResetPasswordEmail(Member member) {
        //Check if the member has a token
        Token existingToken = tokenService.findByUser(member.getUser());
        if (existingToken != null) {
            // Delete the token
            tokenService.delete(existingToken);
        }
        // Generate a token
        String tokenString = UUID.randomUUID().toString();
        User user = member.getUser();
        Token newToken = new Token(tokenString,user);
        // Save the token
        tokenService.save(newToken);

        // Send email
        String to = member.getUser().getEmail();
        String subject = "Password Reset Request";
        String text = "To reset your password, please click the link below:\n"
                + "http://localhost:5000/reset-password?token=" + newToken.getToken() + "\nThis token will expire in 24 hours.";
//                + "http://coder-025.eu-central-1.elasticbeanstalk.com/reset-password?token=" + newToken.getToken() + "\nThis token will expire in 24 hours.";
        sendEmail(to, subject, text);

    }

    // Send reset password email for admin
    public void sendResetPasswordEmail(Admin admin) {
        //Check if the member has a token
        Token existingToken = tokenService.findByUser(admin.getUser());
        if (existingToken != null) {
            // Delete the token
            tokenService.delete(existingToken);
        }

        // Generate a token
        String tokenString = UUID.randomUUID().toString();
        User user = admin.getUser();
        Token newToken = new Token(tokenString,user);
        // Save the token
        tokenService.save(newToken);
        userService.update(user);
        // Save the admin
        adminService.update(admin);

        // Send email
        String to = admin.getUser().getEmail();
        String subject = "Password Reset Request";
        String text = "To reset your password, please click the link below:\n"
                + "http://localhost:5000/reset-password?token=" + newToken.getToken() + "\nThis token will expire in 24 hours.";
//                + "http://coder-025.eu-central-1.elasticbeanstalk.com/reset-password?token=" + token.getToken() + "\nThis token will expire in 24 hours.";

        sendEmail(to, subject, text);
    }


}
