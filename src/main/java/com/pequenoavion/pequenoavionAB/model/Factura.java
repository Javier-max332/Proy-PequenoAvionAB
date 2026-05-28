package com.pequenoavion.pequenoavionAB.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
// ==================
// Clase FACTURA
// ==================
@Entity
@Table(name = "FACTURA")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fact_id") private Integer id;
    
    @Column(name = "pago_id") private Integer pagoId;
    @Column(name = "fact_rfc") private String rfc;
    @Column(name = "fact_razon_social") private String razonSocial;
    @Column(name = "fact_fecha_emision") private LocalDateTime fechaEmision;

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getPagoId() { return pagoId; }
    public void setPagoId(Integer pagoId) { this.pagoId = pagoId; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
}