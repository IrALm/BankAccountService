package com.example.BanqueApp.login.service;

import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Déclare une classe qui
 * implémente UserDetailsService,
 * l’interface Spring Security chargée de
 * → retrouver un utilisateur
 * à partir d’un identifiant (souvent email ou username).
 * Spring Security appellera
 * automatiquement cette classe lors d'une authentification.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Méthode imposée par UserDetailsService.
     * Elle reçoit ici l’email comme
     * identifiant (dans Spring Security,
     * le nom "username" est générique).
     * Elle doit retourner un UserDetails,
     * l’objet utilisateur utilisé par Spring Security.
     * Si aucun utilisateur n’est trouvé →
     * elle doit lancer UsernameNotFoundException.
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Récupération de l'utilisateur par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        /**
         * Cette ligne crée un objet UserDetails standard fourni par Spring Security :
         * 1. user.getEmail()
         * Nom d'utilisateur transmis à Spring Security.
         * Correspond à l'identifiant pour l’authentification.
         * 2. user.getPassword()
         * Le mot de passe déjà encodé en Base de données (via BCrypt normalement).
         * Spring Security compare ce mot de passe avec celui fourni à la connexion.
         * 3. Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
         * Liste des rôles / autorités de l’utilisateur → sous forme d’un seul élément.
         * SimpleGrantedAuthority("ROLE_ADMIN") par exemple.
         * ⚠️ Attention :
         * si tu utilises .hasRole("ADMIN") dans ta config,
         * alors user.getRole().name() doit déjà contenir ROLE_ADMIN, ROLE_CLIENT, etc.
         * Sinon Spring Security ne reconnaîtra pas le rôle.
         */
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );

    }
}

