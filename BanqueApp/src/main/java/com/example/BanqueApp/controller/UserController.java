package com.example.BanqueApp.controller;

import com.example.BanqueApp.login.jwt.JwtUtils;
import com.example.BanqueApp.login.service.UserDetailsServiceImpl;
import com.example.BanqueApp.entity.Customer;
import com.example.BanqueApp.entity.User;
import com.example.BanqueApp.repository.UserRepository;
import com.example.BanqueApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Operation(
            summary = "Ajoute un nouvelle utilisateur : pas encore client ",
            description = "Retourne l'utilisateur crée dans le système."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "utilisateur crée avec succès",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide (paramètres incorrects ou format non supporté)"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/creation")
    public User createUser(@RequestBody User user){
        // Générer un mot de passe aléatoire
        String randomPassword = java.util.UUID.randomUUID().toString().substring(0, 8);
        return userService.createUser(user.getEmail() , randomPassword , user.getRole().name());
    }

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody com.example.BanqueApp.model.createDTO.LoginDTO loginDTO, jakarta.servlet.http.HttpServletResponse response){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
            );
            if(authentication.isAuthenticated()){
                String token = jwtUtils.generateToken(loginDTO.email());
                
                // Créer le cookie
                jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwt_token", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60); // 1 jour
                response.addCookie(cookie);

                Map<String , Object> authData = new HashMap<>();
                authData.put("token" , token);
                authData.put("type", "Bearer ");
                return ResponseEntity.ok(authData);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide !");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe invalide !");
        }
    }


}
