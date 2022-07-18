package org.booking.java.repository;

import org.booking.java.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita,Long> {

}
