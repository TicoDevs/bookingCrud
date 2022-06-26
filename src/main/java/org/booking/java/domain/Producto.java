package org.booking.java.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String descripcion;
    private int cantidad;
    private Boolean estado;
    @ElementCollection(targetClass=String.class)
    private Set<String> img;
    private String codigo;
}
