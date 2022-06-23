package org.booking.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {

    private long id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private Boolean estado;
    private byte img;
    private String codigo;

    public ProductoDto(String nombre, String descripcion, int cantidad, Boolean estado, byte img, String codigo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.estado = estado;
        this.img = img;
        this.codigo = codigo;
    }
}
