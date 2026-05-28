package com.pequenoavion.pequenoavionAB.model;

import jakarta.persistence.*;
// ==================
// Clase CATEGORIA
// ==================
@Entity
@Table(name = "CATEGORIA")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Integer id; 

    @Column(name = "cat_nombre", unique = true, nullable = false)
    private String nombre;

    @Column(name = "cat_descripcion", columnDefinition = "TEXT")
    private String descripcion;

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}