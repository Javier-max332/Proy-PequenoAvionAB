package com.pequenoavion.pequenoavionAB.repository;
import com.pequenoavion.pequenoavionAB.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la tabla de Proveedor en la base de datos.
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {}