package com.pequenoavion.pequenoavionAB.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
// ==================
// Clase PRODUCTO
// ==================
@Entity
@Table(name = "PRODUCTO")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Integer id;

    // Cambiados a Integer para coincidir con el INT de MySQL
    @Column(name = "prov_id")
    private Integer proveedorId;

    @Column(name = "cat_id")
    private Integer categoriaId;

    @Column(name = "prod_sku", unique = true, nullable = false)
    private String sku;

    @Column(name = "prod_modelo")
    private String modelo;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "prod_nombre")
    private String nombre;

    @Column(name = "prod_descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío")
    @Min(value = 0, message = "El precio debe ser mayor a 0")
    @Column(name = "prod_precio_base")
    private BigDecimal precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "prod_stock")
    private Integer stock;

    @Column(name = "prod_marca")
    private String marca;

    // Cambiado para mayor claridad
    @Column(name = "prod_costo_adquisicion")
    private BigDecimal costoAdquisicion;

    // Cambiado a CamelCase (estándar de Java)
    @Column(name = "prod_fecha_ingreso")
    private LocalDateTime fecha_ingreso;

    @Column(name = "prod_estado_producto")
    private String estadoProducto;

    // Cambiado a CamelCase
    @Column(name = "prod_imagen_url")
    private String imagenUrl;

    // --- GETTERS Y SETTERS ---
    // (Genera los getters y setters aquí dando clic derecho en NetBeans -> Insert Code -> Getter and Setter -> Selecciona todos)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }

    public Integer getCategoriaId() { return categoriaId;}
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public BigDecimal getCostoAdquisicion() { return costoAdquisicion; }
    public void setCostoAdquisicion(BigDecimal costoAdquisicion) { this.costoAdquisicion = costoAdquisicion;}

    public LocalDateTime getFecha_ingreso() { return fecha_ingreso; }
    public void setFecha_ingreso(LocalDateTime fecha_ingreso) {this.fecha_ingreso = fecha_ingreso; }

    public String getEstadoProducto() { return estadoProducto; }
    public void setEstadoProducto(String estadoProducto) { this.estadoProducto = estadoProducto; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    @PrePersist
    @PreUpdate
    public void calcularEstadoAutomatico() {
        // 1. Si es un producto nuevo, le ponemos la fecha de hoy
        if (this.fecha_ingreso == null) {
            this.fecha_ingreso = LocalDateTime.now();
        }

        // 2. Seguridad anti-nulos en el stock
        if (this.stock == null) {
            this.stock = 0;
        }

        // 3. Regla de negocio: ACTIVO si hay más de 0, AGOTADO si es 0 o menos
        if (this.stock > 0) {
            this.estadoProducto = "ACTIVO";
        } else {
            this.estadoProducto = "AGOTADO";
        }
    }

}
