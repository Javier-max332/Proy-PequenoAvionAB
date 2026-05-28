package com.pequenoavion.pequenoavionAB.model; // Ajusta tu paquete si es diferente

import jakarta.persistence.*;
// ==================
// Clase DIRECCION
// ==================
@Entity
@Table(name = "DIRECCION") 
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dir_id")
    private Integer id;

    // Aquí guardamos de quién es esta dirección
    @Column(name = "usu_id")
    private Integer usuarioId;

    @Column(name = "dir_pais") private String pais;
    @Column(name = "dir_estado") private String estado;
    @Column(name = "dir_municipio") private String municipio;
    @Column(name = "dir_calle") private String calle;
    @Column(name = "dir_num_exterior") private String numExterior;
    @Column(name = "dir_num_interior") private String numInterior;
    @Column(name = "dir_codigo_postal") private String codigoPostal;

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumExterior() { return numExterior; }
    public void setNumExterior(String numExterior) { this.numExterior = numExterior; }

    public String getNumInterior() { return numInterior; }
    public void setNumInterior(String numInterior) { this.numInterior = numInterior; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    
}