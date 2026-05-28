package com.pequenoavion.pequenoavionAB.repository;

import com.pequenoavion.pequenoavionAB.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Conecta la aplicación con la tabla de Pedidos (las compras generales) en la base de datos.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
    /**
     * Busca el historial de todos los pedidos hechos por un cliente en específico.
     */
    List<Pedido> findByUsuarioId(Integer usuarioId);
    
    /**
     * Busca los pedidos de un cliente, ordenándolos desde la compra más reciente a la más antigua.
     */
    List<Pedido> findByUsuarioIdOrderByFechaCompraDesc(Integer usuarioId);
    
    /**
     * Busca todos los pedidos que tengan un estado en particular (por ejemplo, "PENDIENTE").
     */
    List<Pedido> findByEstadoEnvio(String estado);
    
}