package com.tuproyecto.levelupgamer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table(name = "products")
public class Product {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long es más estándar que Int

    private String name;

    // --- CAMBIO AQUÍ ---
    // 1. CAMBIADO: Tu app usa 'Double' para el precio [cite: 938]
    private double price;

    // 2. ELIMINADOS: 'description' y 'imageUrl' no están
    //    en el modelo de tu app de Android.
}