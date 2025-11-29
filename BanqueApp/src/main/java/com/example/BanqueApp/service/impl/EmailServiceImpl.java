package com.example.BanqueApp.service.impl;

import com.example.BanqueApp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void envoyerEmailNouveauClient(String destinataire, String nom, String prenom, String email, String motDePasse, String numeroCompte) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destinataire);
            helper.setSubject("Bienvenue à BanqueApp - Votre compte a été créé");

            String htmlContent = String.format("""
                    <html>
                        <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                            <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                                <h2 style="color: #2c3e50;">Bienvenue chez BanqueApp !</h2>
                                <p>Bonjour %s %s,</p>
                                <p>Votre compte client a été créé avec succès. Voici vos informations de connexion :</p>
                                
                                <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;">
                                    <p><strong>Email :</strong> %s</p>
                                    <p><strong>Mot de passe temporaire :</strong> %s</p>
                                    <p><strong>Numéro de compte :</strong> %s</p>
                                </div>
                                
                                <p style="color: #e74c3c;"><strong>Important :</strong> Veuillez changer votre mot de passe lors de votre première connexion.</p>
                                
                                <p>Cordialement,<br>L'équipe BanqueApp</p>
                            </div>
                        </body>
                    </html>
                    """, prenom, nom, email, motDePasse, numeroCompte);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }

    @Override
    public void envoyerEmailNouveauConseiller(String destinataire, String nom, String prenom, String email, String motDePasse, String matricule) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destinataire);
            helper.setSubject("Bienvenue à BanqueApp - Compte Conseiller Bancaire");

            String htmlContent = String.format("""
                    <html>
                        <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                            <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                                <h2 style="color: #2c3e50;">Bienvenue dans l'équipe BanqueApp !</h2>
                                <p>Bonjour %s %s,</p>
                                <p>Votre compte conseiller bancaire a été créé avec succès. Voici vos informations de connexion :</p>
                                
                                <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;">
                                    <p><strong>Email :</strong> %s</p>
                                    <p><strong>Mot de passe temporaire :</strong> %s</p>
                                    <p><strong>Matricule :</strong> %s</p>
                                </div>
                                
                                <p style="color: #e74c3c;"><strong>Important :</strong> Veuillez changer votre mot de passe lors de votre première connexion.</p>
                                
                                <p>Cordialement,<br>L'équipe BanqueApp</p>
                            </div>
                        </body>
                    </html>
                    """, prenom, nom, email, motDePasse, matricule);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }
    @Override
    public void envoyerEmailMotDePasseTemporaire(String destinataire, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destinataire);
            helper.setSubject("Bienvenue sur BanqueApp - Vos identifiants");

            String htmlContent = String.format("""
                <html>
                    <body style="font-family: Arial, sans-serif;">
                        <div style="background-color: #f8fafc; padding: 20px;">
                            <div style="background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                                <h2 style="color: #2563eb;">Bienvenue sur BanqueApp !</h2>
                                <p>Votre compte a été créé avec succès.</p>
                                <p>Voici votre mot de passe temporaire pour vous connecter et finaliser votre inscription :</p>
                                <div style="background-color: #eff6ff; padding: 15px; border-radius: 5px; margin: 20px 0;">
                                    <p style="margin: 0;"><strong>Mot de passe :</strong> <span style="font-family: monospace; font-size: 1.2em;">%s</span></p>
                                </div>
                                <p>Connectez-vous dès maintenant pour compléter votre profil.</p>
                                <a href="http://localhost:8081/login" style="display: inline-block; background-color: #2563eb; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Se connecter</a>
                            </div>
                        </div>
                    </body>
                </html>
                """, password);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }
}
