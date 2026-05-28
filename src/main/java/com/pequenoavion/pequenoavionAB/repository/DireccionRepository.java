package com.pequenoavion.pequenoavionAB.repository;

import com.pequenoavion.pequenoavionAB.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Conecta la aplicación con la tabla de Direccion en la base de datos.
 */
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    
    // Cambiamos Optional por List para soportar múltiples direcciones
    List<Direccion> findByUsuarioId(Integer usuarioId);
}