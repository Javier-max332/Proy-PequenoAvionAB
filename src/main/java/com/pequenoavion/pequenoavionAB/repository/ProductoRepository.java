package com.pequenoavion.pequenoavionAB.repository;

import com.pequenoavion.pequenoavionAB.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la tabla de Productos en la base de datos.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Busca la lista de todos los productos que pertenecen a una categoría en específico 
     * (por ejemplo, buscar todos los "Procesadores" o todas las "Tarjetas Madre").
     */
    List<Producto> findByCategoriaId(Integer categoriaId);
    
}