package com.tuproyecto.levelupgamer.controller; // <-- El nuevo paquete

import com.tuproyecto.levelupgamer.Product; // Importamos el Modelo
import com.tuproyecto.levelupgamer.repository.ProductRepository; // Importamos el Repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController // <-- Le dice a Spring que esto es un Controlador de API REST
@RequestMapping("/api/products") // <-- Define la URL base para esta clase
public class ProductController {

    // Inyección de Dependencias:
    // Spring automáticamente nos "pasa" el repositorio que creamos.
    @Autowired
    private ProductRepository productRepository;

    // Este método responderá a las peticiones GET en "/api/products"
    // (Exactamente lo que llama tu ProductList.jsx)
    @GetMapping
    public List<Product> getAllProducts() {
        // Usamos el repositorio para buscar todos los productos en la BD
        // y los retornamos. Spring los convertirá a JSON automáticamente.
        return productRepository.findAll();
    }
}