package com.pequenoavion.pequenoavionAB.service;

import com.pequenoavion.pequenoavionAB.model.Usuario;
import com.pequenoavion.pequenoavionAB.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio de Detalles de Usuario (DetallesUsuarioService)
 * Es el traductor entre tu base de datos y el sistema de seguridad de Spring.
 * Se encarga de buscar a los usuarios cuando intentan iniciar sesión y asignarles
 * su nivel de permisos (para saber si es un cliente normal o el administrador).
 */
@Service
public class DetallesUsuarioService implements UserDetailsService {

    // Herramienta para buscar usuarios en MySQL
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Método de inicio de sesión. Spring Security lo llama automáticamente 
     * justo en el momento en el que el cliente le da clic al botón de "Entrar".
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. BÚSQUEDA: Buscamos al usuario en la base de datos
        // Si no existe, rechazamos el acceso inmediatamente mostrando un error
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 2. EL TRUCO DE LOS ROLES: Asignamos permisos manualmente en el código
        String rol = "ROLE_USER"; // Por defecto, todos los que se registran son clientes normales
        
        // Si el usuario que entra es tu cuenta especial, le damos el "gafete" de administrador
        if ("JavierTorresAdmin1".equalsIgnoreCase(username)) {
            rol = "ROLE_ADMIN";
        }

        // 3. EMPAQUETADO: Construimos el objeto exacto que Spring Security exige 
        // para dejarlo pasar, pasándole el nombre, la contraseña encriptada y su rol.
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(rol) // Aquí inyectamos el permiso que calculamos arriba
                .build();
    }
}