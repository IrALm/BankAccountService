package com.example.BanqueApp.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * une classe utilitaire qui gère la création,
 * la signature, la lecture et la validation des tokens JWT.
 */
@Component
public class JwtUtils {

    @Value("${jwt-secret-key}")
    private String secretKey;

    @Value("${jwt-expiration-time}")
    private Long expirationTime;

    /**
     * Méthode publique pour générer un token JWT à partir d’un email.
     * @param email
     * @return
     */
    public String generateToken(String email){

        Map<String , Object> claims = new HashMap<>();
        return  createToken(claims , email);
    }

    /**
     * Méthode privée qui génère le token.
     * subject = identité principale dans le JWT → ici l’email.
     * @param claims
     * @param subjet
     * @return
     */
    private String createToken(Map<String , Object> claims , String subjet ){

        return Jwts.builder() // Utilise la librairie JJWT pour construire un token.
                .setClaims(claims) //Ajoute les claims supplémentaires (optionnels)
                .setSubject(subjet) // Définit le subject, c’est-à-dire l’identifiant de l’utilisateur (email)
                .setIssuedAt(new Date(System.currentTimeMillis()))//Date à laquelle le token a été créé
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))//Définit la date d’expiration à partir de maintenant + la durée injectée
                /**
                 * Signe le token avec :
                 * la clé secrète
                 * l’algorithme HS256 (HMAC SHA-256)
                 */
                .signWith(getSignKey() , SignatureAlgorithm.HS256)
                .compact();//Finalise la création et retourne le JWT au format String
    }

    /**
     * Méthode interne pour transformer la clé secrète en clé de signature
     * @return
     */
    private Key getSignKey(){

        byte[] keyBytes = secretKey.getBytes();//Convertit la clé secrète en tableau d’octets
        /**
         * Crée une clé cryptographique (java.security.Key) compatible avec HS256.
         * SecretKeySpec = type de clé symétrique
         */
        return new SecretKeySpec(keyBytes , SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * Méthode qui valide un JWT pour un utilisateur donné
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token , UserDetails userDetails){
        String useremail = extractUserEmail(token);//Extrait l’email contenu dans le token
        /**
         * Deux conditions pour que le token soit valide :
         * L’email du token = l’utilisateur authentifié
         * Le token n’est pas expiré
         */
        return (useremail.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    /**
     * Extrait la date d’expiration du token.
     * Vérifie si elle est avant la date actuelle → donc expiré
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    /**
     * Extrait le subject → ici l’email.
     * Utilise une syntaxe fonctionnelle (Java 8)
     * @param token
     * @return
     */
    public String extractUserEmail(String token){
        return extractClaim(token , Claims::getSubject);
    }

    /**
     * Extrait la date d’expiration
     * @param token
     * @return
     */
    private Date extractExpirationDate(String token){
        return extractClaim(token , Claims::getExpiration);
    }

    /**
     * Récupère toutes les claims
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    private <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);//Récupère toutes les claims.
        return claimsResolver.apply(claims);//Applique la fonction passée en paramètre (ex: Claims::getSubject).
    }

    /**
     * Crée un parseur JWT.
     * Utilise la même clé secrète que celle utilisée pour signer.
     * Parse le token → vérifie la signature cryptographique.
     * Retourne le body (les claims).
     * Si la signature ne correspond pas → exception (token altéré ou faux
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())   // ici ça ne sera pas deprecated
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Un claim (ou revendication en français, mais on utilise toujours claim)
     * est une information contenue à l’intérieur d’un token JWT
     */

}
