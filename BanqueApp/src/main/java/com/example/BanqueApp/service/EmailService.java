package com.example.BanqueApp.service;

public interface EmailService {

    void envoyerEmailNouveauClient(String destinataire, String nom, String prenom, String email, String password, String numeroCompte);
    
    void envoyerEmailNouveauConseiller(String destinataire, String nom, String prenom, String email, String password, String matricule);

    void envoyerEmailMotDePasseTemporaire(String destinataire, String password);
}
