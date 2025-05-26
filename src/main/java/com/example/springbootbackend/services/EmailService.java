package com.example.springbootbackend.services;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    public void sendPasswordResetEmail(String toEmail, String userName, String resetUrl) throws IOException {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        String subject = "Password Reset Link";

        String htmlContent = "<div style='background-color: #ffffff; padding: 20px; border-radius: 16px; text-align: center;'>"
                + "<img src='https://studyspotr.s3.us-east-2.amazonaws.com/studySpotrLogoOrange.png' style='width: 64px; margin: 0 auto 16px;'>"
                + "<h2 style='font-family: sans-serif; font-size: 20px; color: #ed8936; margin-bottom: 8px;'>Password Reset Request</h2>"
                + "<p style='font-family: sans-serif; font-size: 16px; margin-bottom: 4px;'>Hi <strong>" + userName + "</strong>,</p>"
                + "<p style='font-family: sans-serif; font-size: 18px; margin-bottom: 4px;'>We received a request to reset your password.</p>"
                + "<p style='font-family: sans-serif; font-size: 18px; margin-bottom: 16px;'>Please click the button below to reset it:</p>"
                + "<a href='" + resetUrl + "' target='_blank' style='display: inline-block; background-color: #ed8936; color: #ffffff; padding: 12px 24px; border-radius: 16px; margin-bottom: 24px; text-decoration: none;'>Click to reset password</a>"
                + "<p style='font-family: sans-serif; font-size: 13px; color: #999999; margin-bottom: 4px;'>If the button does not work, click this link instead:</p>"
                + "<p style='font-family: sans-serif; font-size: 13px; color: #0000FF; margin-bottom: 24px;'>" + resetUrl + "</p>"
                + "<p style='font-family: sans-serif; font-size: 12px; color: #999999;'>If you did not request a password reset, please ignore this email.</p>"
                + "</div>";

        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        // Disable click tracking
        TrackingSettings trackingSettings = new TrackingSettings();
        ClickTrackingSetting clickTrackingSetting = new ClickTrackingSetting();
        clickTrackingSetting.setEnable(false);
        clickTrackingSetting.setEnableText(false);
        trackingSettings.setClickTrackingSetting(clickTrackingSetting);
        mail.setTrackingSettings(trackingSettings);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());
    }
}
