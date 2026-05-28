package com.pequenoavion.pequenoavionAB.repository;
import com.pequenoavion.pequenoavionAB.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Conecta la aplicación con la tabla de Categorías en la base de datos.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}