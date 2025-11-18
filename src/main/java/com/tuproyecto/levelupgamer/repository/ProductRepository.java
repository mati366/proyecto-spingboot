package com.tuproyecto.levelupgamer.repository; // <-- Nota el .repository

import org.springframework.data.jpa.repository.JpaRepository;
import com.tuproyecto.levelupgamer.Product; // <-- Importa tu Entidad

// JpaRepository<[ClaseDeEntidad], [TipoDeDatoDelID]>
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring mágicamente nos da:
    // - findAll() (Para tu ProductList)
    // - save() (Para crear/actualizar productos)
    // - deleteById() (Para el AdminPage)
    // - findById()
    // ¡Y más!
}