package com.pequenoavion.pequenoavionAB.model;

import java.math.BigDecimal;

public class ItemCarrito {
    
    private Integer productoId;
    private String nombre;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private String imagenUrl;

    // Constructor vacío
    public ItemCarrito() {}

    // Constructor con datos
    public ItemCarrito(Integer productoId, String nombre, Integer cantidad, BigDecimal precioUnitario, String imagenUrl) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.imagenUrl = imagenUrl;
        this.subtotal = precioUnitario.multiply(new BigDecimal(cantidad));
    }

    // --- GETTERS Y SETTERS ---
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad; 
        this.subtotal = this.precioUnitario.multiply(new BigDecimal(cantidad)); // Actualiza el subtotal automáticamente
    }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}