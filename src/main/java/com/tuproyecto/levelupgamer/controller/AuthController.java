package com.tuproyecto.levelupgamer.controller;

import com.tuproyecto.levelupgamer.User; // Tu Entidad
import com.tuproyecto.levelupgamer.repository.UserRepository; // Repositorio de User
import com.tuproyecto.levelupgamer.dto.AuthResponse; // DTO de respuesta
import com.tuproyecto.levelupgamer.dto.LoginRequest; // DTO de login
import com.tuproyecto.levelupgamer.dto.RegisterRequest; // DTO de registro
import com.tuproyecto.levelupgamer.service.JwtService; // Servicio de JWT
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

        // --- CAMBIOS AQUÍ ---

        // 1. Creamos un nuevo usuario
        User newUser = new User();
        newUser.setEmail(request.getUsername()); // React envía 'username' como email
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // 2. AÑADIDO: Guardamos la fecha de nacimiento que viene en el request
        newUser.setBirthDate(request.getBirthDate()); 
        
        // 3. AÑADIDO: Asignamos el rol por defecto
        newUser.setRole("user"); 

        // 4. Guardamos el usuario en la base de datos
        User savedUser = userRepository.save(newUser);

        // --- FIN DE LOS CAMBIOS ---

        // Generamos un token para que inicie sesión automáticamente
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            savedUser.getEmail(),
            savedUser.getPassword(),
            java.util.Collections.emptyList() 
        );
        
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    // El método login() no necesita cambios
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), // El email
                request.getPassword()  // La contraseña
            )
        );
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}