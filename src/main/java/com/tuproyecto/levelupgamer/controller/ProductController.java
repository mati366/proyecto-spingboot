package com.tuproyecto.levelupgamer.controller;

import com.tuproyecto.levelupgamer.Product;
import com.tuproyecto.levelupgamer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; // Importa todas las anotaciones web
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 1. LEER TODOS (GET)
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. CREAR PRODUCTO (POST)
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 3. MODIFICAR PRODUCTO (PUT)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        // Buscamos el producto por ID, si no existe lanza error
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        
        // Actualizamos sus datos con los que vienen del cuerpo de la petición
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        
        // Guardamos los cambios en la base de datos
        return productRepository.save(product);
    }

    // 4. ELIMINAR PRODUCTO (DELETE)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
