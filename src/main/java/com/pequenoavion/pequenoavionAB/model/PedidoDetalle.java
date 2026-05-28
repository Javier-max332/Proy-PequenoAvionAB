package com.pequenoavion.pequenoavionAB.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
// ==================
// Clase PEDIDO_DETALLE
// ==================
@Entity
@Table(name = "PEDIDO_DETALLE")
public class PedidoDetalle {

    // Aquí inyectamos la llave que creamos arriba
    @EmbeddedId
    private PedidoDetalleId id;

    @Column(name = "det_cantidad")
    private Integer cantidad;

    @Column(name = "det_precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name = "det_importe")
    private BigDecimal importe;

    // --- GETTERS Y SETTERS ---   
    public PedidoDetalleId getId() { return id; }
    public void setId(PedidoDetalleId id) { this.id = id; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
}