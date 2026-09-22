package com.monconcours.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void envoyerCodeConnexion(
            String email,
            String code,
            String lien) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Votre compte Mon Concours");

        message.setText(
                "Votre compte Mon Concours a ete cree.\n\n" +

                        "Votre code de connexion est : " + code + "\n\n" +

                        "Vous pouvez utiliser ce code pour vous connecter.\n\n" +

                        "Vous pouvez également choisir votre propre mot de passe en cliquant sur ce lien :\n" +
                        lien + "\n\n" +

                        "Ce lien est valable pendant 15 minutes."
        );

        mailSender.send(message);
    }

    public void envoyerLienReinitialisation(String email, String lien) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(
                "Réinitialisation de votre mot de passe - Mon Concours"
        );

        message.setText(
                "Vous avez demandé la réinitialisation de votre mot de passe.\n\n" +
                        "Cliquez sur ce lien pour choisir un nouveau mot de passe :\n" +
                        lien + "\n\n" +
                        "Ce lien est valable pendant 15 minutes.\n\n" +
                        "Si vous n'avez pas demandé cette réinitialisation, ignorez cet email."
        );

        mailSender.send(message);
    }
}