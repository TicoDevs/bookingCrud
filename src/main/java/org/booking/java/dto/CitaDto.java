package org.booking.java.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CitaDto {

    private long id;
    @NotNull
    private Date cita;
    @NotNull
    private boolean estado;
    @NotNull
    @Size(min = 1, message = "Al menos debe de agregar un tipo")
    private Set<String> tipo;

    public CitaDto(Date cita, boolean estado, Set<String> tipo) {
        this.cita = cita;
        this.estado = estado;
        this.tipo = tipo;
    }
}
