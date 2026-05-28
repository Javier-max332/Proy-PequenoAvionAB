package com.pequenoavion.pequenoavionAB.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
// ==================
// Clase PAGO
// ==================
@Entity
@Table(name = "PAGO")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Integer id;

    @Column(name = "ped_id", unique = true, nullable = false)
    private Integer pedidoId; // Relación 1 a 1 con la tabla Pedido

    @Column(name = "pago_metodo_pago", length = 50)
    private String metodoPago;

    @Column(name = "pago_monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "pago_estado_pago", length = 50)
    private String estadoPago; // Por defecto será 'PENDIENTE'

    // Constructores
    public Pago() {}

    // --- GETTERS AND SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getPedidoId() { return pedidoId; }
    public void setPedidoId(Integer pedidoId) { this.pedidoId = pedidoId; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
}