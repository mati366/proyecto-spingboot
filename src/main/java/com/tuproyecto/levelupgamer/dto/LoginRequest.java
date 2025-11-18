package com.tuproyecto.levelupgamer.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // Este es el email
    private String password;
}