package com.pequenoavion.pequenoavionAB.repository;
import com.pequenoavion.pequenoavionAB.model.PedidoDetalle;
import com.pequenoavion.pequenoavionAB.model.PedidoDetalleId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la tabla de Detalles del Pedido (los productos dentro de cada compra).
 */
@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, PedidoDetalleId> {
    
    /**
     * Busca la lista de todos los productos que pertenecen a un pedido en específico.
     */
    List<PedidoDetalle> findByIdPedidoId(Integer pedidoId);
}