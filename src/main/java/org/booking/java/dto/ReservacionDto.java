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
public class ReservacionDto {
    private long id;
    @NotNull
    private Date fechaReservacion;
    @NotNull
    private Boolean estado;
    private Set<String> tipo;
    private long idMiembro;
    private long idProducto;
    private long idCita;

    public ReservacionDto (Date fechaReservacion, boolean estado, Set<String>tipo,long idCita, long idMiembro, long idProducto){
        this.fechaReservacion = fechaReservacion;
        this.estado = estado;
        this.tipo = tipo;
        this.idCita = idCita;
        this.idMiembro = idMiembro;
        this.idProducto = idProducto;
    }


}
