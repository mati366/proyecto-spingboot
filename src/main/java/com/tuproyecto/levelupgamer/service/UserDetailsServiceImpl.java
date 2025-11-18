package com.tuproyecto.levelupgamer.service;

import com.tuproyecto.levelupgamer.User; // Importa tu entidad User
import com.tuproyecto.levelupgamer.repository.UserRepository; // Importa tu repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // Para la lista de roles

@Service // Le dice a Spring que esto es un Servicio
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Spring Security llama a este método.
        // Nosotros le decimos que "username" es en realidad el "email".
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Convertimos nuestro User (de la BD) a un User (de Spring Security)
        // Dejamos la lista de roles vacía por ahora para simplificar.
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(), // El password ya hasheado de la BD
            new ArrayList<>() // Lista de roles (vacía por ahora)
        );
    }
}