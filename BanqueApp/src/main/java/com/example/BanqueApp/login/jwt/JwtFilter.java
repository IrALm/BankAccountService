package com.example.BanqueApp.login.jwt;

import com.example.BanqueApp.login.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * OncePerRequestFilter = filtre Spring Security
 * exécuté une seule fois par requête HTTP.
 * Cela garantit que le JWT est traité
 * une seule fois, même si
 * la requête traverse plusieurs filtres.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    /**
     * Méthode exécutée pour chaque requête HTTP.
     * Mon but :
     * Extraire le token
     * Le valider
     * Authentifier l’utilisateur si besoin
     * Laisser la requête continuer
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request , HttpServletResponse response , FilterChain filterChain) throws ServletException, IOException {

        /**
         * Récupère le header "Authorization" (si le token est passé via Bearer <token>).
         * Initialise useremail et jwt.
         */
        String authHeader = request.getHeader("Authorization");
        String useremail = null;
        String jwt = null;

        /**
         * Vérifie si le header commence par "Bearer "
         * Si oui → récupère le JWT en coupant après "Bearer "
         * (7 caractères)
         */
        if( authHeader != null && authHeader.startsWith("Bearer ")){
            jwt = authHeader.substring(7);
        } else { //Si pas dans le header → chercher dans les cookies
            // Chercher dans les cookies
            if (request.getCookies() != null) {
                for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                    if ("jwt_token".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
        }

        /**
         * Si aucun header Authorization,
         * regarde si le JWT se trouve dans un cookie nommé
         * "jwt_token".
         * Permet d'accepter deux modes de passage :
         * Header Bearer
         * Cookie HttpOnly
         */

        if (jwt != null) {
            try {
                useremail = jwtUtils.extractUserEmail(jwt);
            } catch (ExpiredJwtException e) {
                System.out.println("⚠ Token expiré");
                SecurityContextHolder.clearContext(); // Supprime toute authentification
            } catch (Exception e) {
                System.out.println("⚠ Token invalide");
                SecurityContextHolder.clearContext();
            }
        }

        if( useremail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            /**
             * On ne fait la validation que si :
             * Un email a été extrait
             * Aucun utilisateur n’est déjà authentifié dans
             * le SecurityContext (évite d’écraser une
             * auth déjà existante)
             */
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(useremail);
                /**
                 * Charge l’utilisateur depuis la base via
                 * l’email extrait du JWT.
                 * Si l’utilisateur n’existe
                 * plus → exception → token invalide.
                 */
                if(jwtUtils.validateToken(jwt , userDetails)){
                    /**
                     * Vérifie :
                     * que l’email du token = email
                     * utilisateur en base
                     * que le token n’est pas expiré
                     * que la signature est correcte
                     */
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
                    /**
                     * Si tout est valide →
                     * authentifier l’utilisateur dans Spring Security
                     */
                    /**
                     * Crée un objet Authentication contenant :
                     * l’utilisateur (userDetails)
                     * aucun mot de passe (null car pas besoin)
                     * ses rôles (authorities)
                     */
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    /**
                     * Ajoute des détails sur la requête (IP, session, etc.) →
                     * utile pour audit et sécurité.
                     */
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    /**
                     * C’est ici que l’utilisateur est officiellement authentifié pour Spring Security.
                     * Après ça, dans les contrôleurs, tu peux appeler :
                     * SecurityContextHolder.getContext().getAuthentication()
                     * @AuthenticationPrincipal
                     * Principal principal
                     * etc.
                     */
                }
            } catch (Exception e) {
                // Le token est invalide ou l'utilisateur n'existe plus
                // On ne fait rien, l'utilisateur sera considéré comme non authentifié
                SecurityContextHolder.clearContext();//8) Si problème → nettoyer le contexte
                /**
                 * En cas :
                 * token invalide
                 * signature corrompue
                 * expiration dépassée
                 * utilisateur supprimé
                 * Alors → l’utilisateur est considéré comme non authentifié
                 */
            }
        }
        filterChain.doFilter(request ,response);
        /**
         * Très important : laisse les autres filtres s’exécuter.
         * Sans ceci → toute la requête serait bloquée.
         */

    }
}
