package com.tuproyecto.levelupgamer;

import com.tuproyecto.levelupgamer.repository.ProductRepository;
import com.tuproyecto.levelupgamer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LevelupgamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevelupgamerApplication.class, args);
	}

    // Este código se ejecuta cada vez que el servidor arranca
	@Bean
	public CommandLineRunner demoData(UserRepository userRepo, ProductRepository productRepo, PasswordEncoder passwordEncoder) {
		return args -> {
            // 1. CREAR PRODUCTOS (Si no existen)
			if (productRepo.count() == 0) {
				productRepo.save(new Product(null, "Catan", 29990.0));
				productRepo.save(new Product(null, "Controlador Xbox", 59990.0));
				productRepo.save(new Product(null, "PlayStation 5", 549990.0));
                userRepo.save(new Product(null, "Silla Gamer Titan", 349990.0));
                productRepo.save(new Product(null, "Mouse Logitech G502", 49990.0));
				System.out.println("✅ Productos insertados automáticamente.");
			}

            // 2. CREAR ADMIN POR DEFECTO (admin@duoc.cl / admin123)
             if (userRepo.findByEmail("admin@duoc.cl").isEmpty()) {
                 User admin = new User();
                 admin.setEmail("admin@duoc.cl");
                 admin.setPassword(passwordEncoder.encode("admin123"));
                 admin.setBirthDate("01-01-1990");
                 admin.setRole("admin");
                 userRepo.save(admin);
                 System.out.println("👑 Cuenta admin@duoc.cl creada."); [cite: 2]
             }
             
            // 3. PROMOVER CUENTA REGISTRADA (matias@gmail.com)
            String miEmail = "matias@gmail.com"; 
            
            userRepo.findByEmail(miEmail).ifPresent(user -> {
                if (!user.getRole().equals("admin")) {
                    user.setRole("admin");
                    userRepo.save(user);
                    System.out.println("👑 Usuario " + miEmail + " promovido a ADMIN.");
                }
            });
		};
	}
