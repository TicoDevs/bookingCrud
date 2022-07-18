package org.booking.java.repository;

import org.booking.java.domain.Producto;
import org.booking.java.domain.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface ReservacionRepository {

    Optional<Reservacion> findById(long id);
    boolean existsById(long id);


}
