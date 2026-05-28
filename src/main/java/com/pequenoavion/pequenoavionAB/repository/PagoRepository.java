package com.pequenoavion.pequenoavionAB.repository;

import com.pequenoavion.pequenoavionAB.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Conecta la aplicación con la tabla de Pago en la base de datos.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    /**
     * Busca el registro de pago asociado a un pedido específico.
     * Útil para cambiar el estado de PENDIENTE a REALIZADO.
     */
    Optional<Pago> findByPedidoId(Integer pedidoId);
}