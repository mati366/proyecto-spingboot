// ... imports existentes ...
    // Asegúrate de importar:
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.DeleteMapping;
    // ...

    // 1. MODIFICAR PRODUCTO (PUT)
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        // Buscamos el producto por ID
        Product product = productRepository.findById(id).orElseThrow();
        
        // Actualizamos sus datos
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        
        // Guardamos los cambios
        return productRepository.save(product);
    }

    // 2. ELIMINAR PRODUCTO (DELETE)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
