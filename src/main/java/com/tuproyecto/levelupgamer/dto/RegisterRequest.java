package com.tuproyecto.levelupgamer.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    // Los nombres deben coincidir con el JSON que envía React/Android
    private String username; // Este será el email
    private String password;
    
    // --- AÑADIDO ---
    // Añadimos el campo para la fecha de nacimiento
    private String birthDate;
}