package com.pequenoavion.pequenoavionAB.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
// ==================
// Clase USUARIO
// ==================
@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Integer id;

    @Column(name = "usu_username", unique = true)
    private String username;

    @Column(name = "usu_password_hash")
    private String password;

    @Column(name = "usu_nombre_completo")
    private String nombreCompleto;
    
    @Column(name = "usu_email")
    private String email;
    
    @Column(name = "usu_telefono")
    private String telefono;

    @Column(name = "usu_fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "usu_fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;
    
    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // --- GETTERS Y SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}