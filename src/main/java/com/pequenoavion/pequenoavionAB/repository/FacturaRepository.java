package com.pequenoavion.pequenoavionAB.repository;

import com.pequenoavion.pequenoavionAB.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Conecta la aplicación con la tabla de Facturas en la base de datos.
 */
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    
    /**
     * Busca una factura específica usando el número de ID del pago.
     */
    Optional<Factura> findByPagoId(Integer pagoId);
}