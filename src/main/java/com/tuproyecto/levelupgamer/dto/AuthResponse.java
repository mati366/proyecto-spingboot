package com.tuproyecto.levelupgamer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // Un constructor para setear el token fácilmente
public class AuthResponse {
    private String token;
}