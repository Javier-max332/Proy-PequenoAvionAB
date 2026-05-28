package com.pequenoavion.pequenoavionAB.model;

import jakarta.persistence.*;
// ==================
// Clase PROVEEDOR
// ==================
@Entity
@Table(name = "PROVEEDOR")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prov_id")
    private Integer id;

    @Column(name = "prov_nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(name = "prov_contacto")
    private String contacto;

    @Column(name = "prov_telefono")
    private String telefono;

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
}