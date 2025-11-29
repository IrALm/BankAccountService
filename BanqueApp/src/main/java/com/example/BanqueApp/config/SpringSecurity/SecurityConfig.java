package com.example.BanqueApp.config.SpringSecurity;

import com.example.BanqueApp.login.jwt.JwtFilter;
import com.example.BanqueApp.login.jwt.JwtUtils;
import com.example.BanqueApp.login.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // pour indiquer que la classe contient des définitions de beans
@EnableWebSecurity /* active la configuration de sécurité pour l'appli :
                      permet l'utilisation de HttpSecurity et SecurityFilterChain*/
@EnableMethodSecurity /* autorise l'usage d'annotations de Sécurité sur les méthodes
                        par ex : @PreAuthorize, @Secured, @PostAuthorize
                        pour protéger des méthodes de services ou contrôleurs au niveau méthode.
*/
@RequiredArgsConstructor /*Annotation Lombok : génère un constructeur
avec tous les champs final en paramètre.
Ici elle permet l’injection par constructeur de userDetailsService
et jwtUtils sans écrire le constructeur manuellement.
*/
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    /**
     * Définit un bean PasswordEncoder
     * utilisé pour chiffrer / vérifier les mots de passe.
     * BCryptPasswordEncoder : algorithme de hachage sécurisé
     * (avec salage et itérations).
     * Par défaut il utilise une certain
     * “strength” (par ex. 10). Ce bean sera
     * utilisé par Spring pour comparer le mot de passe encodé
     * stocké en base et celui fourni lors de l’authentification.
     * @return Password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Déclare un bean AuthenticationManager
     * (manager central qui valide l’authentification).
     * Ici on demande HttpSecurity (partagé par Spring)
     * et le PasswordEncoder défini plus haut en paramètres.
     * @param httpSecurity
     * @param passwordEncoder
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity , PasswordEncoder passwordEncoder) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        /**
         * Configure l’AuthenticationManagerBuilder pour :
         * utiliser userDetailsService pour charger les utilisateurs,
         * utiliser le passwordEncoder pour vérifier le mot de passe (BCrypt).
         * Concrètement : quand on fera une authentification
         * (par exemple username/password), Spring ira chercher
         * l’utilisateur via userDetailsService, récupèrera
         * le mot de passe encodé, puis comparera avec
         * le mot de passe fourni en appelant passwordEncoder.matches(...).
         */
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        /**
         * Construit et retourne l’AuthenticationManager configuré,
         * et le rend disponible comme bean dans le contexte Spring.
         */
        return authenticationManagerBuilder.build();
    }

    /**
     * Déclare le SecurityFilterChain principal
     * (chaîne de filtres Spring Security) pour configurer
     * la sécurité HTTP (URL, filtres, CSRF, etc.). C’est la façon
     * moderne d’assembler la configuration pour les requêtes web.
     * Désactive la protection CSRF.
     * Commentaire : si l'API est RESTful et n'utilise pas
     * de sessions/cookies pour l'authentification
     * mais des tokens (JWT),
     * on désactive souvent CSRF car il vise les attaques
     * via cookies et formulaires.
     * Attention : désactiver CSRF
     * est correct pour des APIs token-based,
     * mais à garder en tête s'il y a des formulaires soumis
     * via session/cookie.
     * configuration des règles d'autorisation pour les requêtes HTTP.
     * auth -> auth
     * reçoit un builder pour définir les requestMatchers et règles.
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // REST → CSRF inutile
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register").permitAll() // Pages publiques
                        .requestMatchers("/user/creation", "/user/connexion").permitAll() // endpoints publics
                        .requestMatchers("/ws/**").permitAll() // WebSocket
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Resources statiques
                        .requestMatchers("/dashboard", "/messaging", "/complete-profile-client", "/complete-profile-advisor").authenticated()
                        .requestMatchers("/api/messaging/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/advisor/**").hasRole("BANK_ADVISOR")
                        .requestMatchers("/customer/**").hasRole("CLIENT")
                        .anyRequest().authenticated() // Toute autre requête non explicitement autorisée ci-dessus doit être authentifiée. C’est une sécurité "par défaut" qui ferme l’accès aux routes non listées.
                )
                /**
                 * Ajoute un filtre personnalisé (JwtFilter)
                 * avant le UsernamePasswordAuthenticationFilter.
                 * Rôle typique du JwtFilter :
                 * Récupérer le token JWT depuis
                 * l’en-tête HTTP (ex. Authorization: Bearer <token>),
                 * Valider le token via jwtUtils,
                 * Si valide, charger l’utilisateur
                 * via userDetailsService et
                 * construire un Authentication
                 * (ex. UsernamePasswordAuthenticationToken)
                 * et le placer dans le SecurityContext.
                 * Pourquoi addFilterBefore(..., UsernamePasswordAuthenticationFilter.class) ?
                 * Le filtre JWT doit s’exécuter avant le filtre standard qui
                 * gère l'authentification par formulaire (username/password).
                 * Ainsi, si la requête porte un JWT valide,
                 * elle sera authentifiée avant d’atteindre le filtre de formulaire.
                 */
                .addFilterBefore(
                        new JwtFilter(userDetailsService, jwtUtils),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

}

