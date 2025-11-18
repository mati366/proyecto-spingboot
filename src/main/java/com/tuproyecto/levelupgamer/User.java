package com.tuproyecto.levelupgamer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// YA NO NECESITAMOS LocalDate
// import java.time.LocalDate; 
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long es más estándar en Spring que Int

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // En tu app se llama 'pass', pero 'password' es más estándar

    // --- CAMBIOS AQUÍ ---
    
    // 1. AÑADIDO: Tu app usa 'birthDate' como String [cite: 949]
    private String birthDate; 

    // 2. AÑADIDO: Tu app usa 'role' [cite: 949]
    private String role; 

    // 3. ELIMINADOS: 'name', 'points' y 'lastRewardClaimed' no están
    //    en el modelo principal de tu app de Android.
}