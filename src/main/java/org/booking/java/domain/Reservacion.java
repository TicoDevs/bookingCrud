package org.booking.java.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;


@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date fechaReservacion;
    private Boolean estado;
    @ElementCollection(targetClass=String.class)
    private Set<String> tipo;
    private long idMiembro;
    private long idProducto;
    private long idCita;


}
