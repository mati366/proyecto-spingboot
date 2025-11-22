package com.tuproyecto.levelupgamer.controller;

import com.tuproyecto.levelupgamer.User;
import com.tuproyecto.levelupgamer.repository.UserRepository;
import com.tuproyecto.levelupgamer.dto.AuthResponse;
import com.tuproyecto.levelupgamer.dto.LoginRequest;
import com.tuproyecto.levelupgamer.dto.RegisterRequest;
import com.tuproyecto.levelupgamer.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Nuevos imports necesarios
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        
        if (userRepository.findByEmail(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Error: El email ya está en uso."));
        }

        User newUser = new User();
        newUser.setEmail(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setBirthDate(request.getBirthDate());
        newUser.setRole("user"); // Por defecto es user

        User savedUser = userRepository.save(newUser);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            savedUser.getEmail(),
            savedUser.getPassword(),
            java.util.Collections.emptyList()
        );
        
        // 🟢 CORRECCIÓN: Añadimos el rol al token
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", savedUser.getRole()); // "user"

        String token = jwtService.generateToken(extraClaims, userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // 🟢 CORRECCIÓN CRÍTICA:
        // Buscamos al usuario en la BD para saber qué rol tiene REALMENTE (ej. "admin")
        User dbUser = userRepository.findByEmail(request.getUsername()).orElseThrow();
        
        // Ponemos ese rol dentro del token
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", dbUser.getRole()); 
        
        // Generamos el token CON el rol dentro
        String token = jwtService.generateToken(extraClaims, userDetails);
        
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
