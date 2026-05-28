package com.pequenoavion.pequenoavionAB.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
// ==================
// Clase PedidoDetalleId
// ==================
@Embeddable
public class PedidoDetalleId implements Serializable {

    @Column(name = "ped_id")
    private Integer pedidoId;

    @Column(name = "prod_id")
    private Integer productoId;

    // 1. Constructor vacío 
    public PedidoDetalleId() {
    }

    // 2. Constructor con 2 parámetros
    public PedidoDetalleId(Integer pedidoId, Integer productoId) {
        this.pedidoId = pedidoId;
        this.productoId = productoId;
    }

    // --- GETTERS Y SETTERS ---
    public Integer getPedidoId() { return pedidoId; }
    public void setPedidoId(Integer pedidoId) { this.pedidoId = pedidoId; }

    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }

    // --- EQUALS Y HASHCODE (Obligatorios para llaves compuestas en Java) ---
    // Son necesarios porque estamos usando dos IDs al mismo tiempo (Pedido y Producto).
    // Spring Boot los usa para no confundir un registro con otro en la base de datos.

    /**
     * Compara si dos registros son exactamente el mismo.
     */
    @Override
    public boolean equals(Object o) {
        // 1. Si es el mismo objeto en memoria, son iguales.
        if (this == o) return true;
        
        // 2. Si el otro está vacío o es de otro tipo diferente, no son iguales.
        if (o == null || getClass() != o.getClass()) return false;
        
        // 3. Comparamos que ambos tengan el mismo ID de pedido y de producto.
        PedidoDetalleId that = (PedidoDetalleId) o;
        return Objects.equals(pedidoId, that.pedidoId) && Objects.equals(productoId, that.productoId);
    }

    /**
     * Crea un número identificador único y rápido (Hash) para esta combinación de IDs.
     * Esto ayuda a que el sistema busque y guarde la información mucho más rápido.
     */
    @Override
    public int hashCode() {
        return Objects.hash(pedidoId, productoId);
    }
}