package com.tuproyecto.levelupgamer.repository; // <-- Nota el .repository

import org.springframework.data.jpa.repository.JpaRepository;
import com.tuproyecto.levelupgamer.User; // <-- Importa tu Entidad
import java.util.Optional; // <-- Importamos Optional

public interface UserRepository extends JpaRepository<User, Long> {

    // Como tu login usa el email, necesitamos un método para "buscar por email".
    // Spring Data JPA es lo suficientemente inteligente para entender
    // el nombre de este método y crear la consulta SQL por nosotros.
    
    // Esto es crucial para el login y el registro.
    Optional<User> findByEmail(String email);
}