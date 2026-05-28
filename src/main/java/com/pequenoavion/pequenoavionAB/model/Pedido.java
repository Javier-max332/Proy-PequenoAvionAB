package com.pequenoavion.pequenoavionAB.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
// ==================
// Clase PEDIDO
// ==================
@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ped_id")
    private Integer id;

    @Column(name = "ped_num_pedido", unique = true)
    private String numPedido;

    @Column(name = "usu_id")
    private Integer usuarioId;

    @Column(name = "dir_id")
    private Integer direccionId;

    @Column(name = "ped_fecha_compra")
    private LocalDateTime fechaCompra;

    @Column(name = "ped_total")
    private BigDecimal total;

    @Column(name = "ped_estado_envio")
    private String estadoEnvio;
    
    @Column(name = "ped_fecha_entrega_estimada")
    private LocalDate fechaEntregaEstimada;        

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNumPedido() { return numPedido; }
    public void setNumPedido(String numPedido) { this.numPedido = numPedido; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public Integer getDireccionId() { return direccionId; }
    public void setDireccionId(Integer direccionId) { this.direccionId = direccionId; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getEstadoEnvio() { return estadoEnvio; }
    public void setEstadoEnvio(String estadoEnvio) { this.estadoEnvio = estadoEnvio; }
    public LocalDate getFechaEntregaEstimada() { return fechaEntregaEstimada; }
    public void setFechaEntregaEstimada(LocalDate fechaEntregaEstimada) { this.fechaEntregaEstimada = fechaEntregaEstimada; }
    
}