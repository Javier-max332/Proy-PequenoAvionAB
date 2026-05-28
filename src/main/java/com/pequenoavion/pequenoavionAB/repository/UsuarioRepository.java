package com.pequenoavion.pequenoavionAB.repository;

import com.pequenoavion.pequenoavionAB.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Conecta la aplicación con la tabla de Usuarios en la base de datos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca a un usuario específico usando su nombre de usuario (username).
     * Sirve principalmente para que puedan iniciar sesión o para revisar 
     * que un nombre no esté repetido cuando alguien nuevo se registra.
     */
    Optional<Usuario> findByUsername(String username);
}