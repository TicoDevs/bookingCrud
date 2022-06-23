package org.booking.java.repository;

import org.booking.java.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Optional<Producto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);

}
