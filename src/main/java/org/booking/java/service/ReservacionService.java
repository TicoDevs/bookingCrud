package org.booking.java.service;

import org.booking.java.dto.ReservacionContent;
import org.booking.java.dto.ReservacionDto;

import java.util.List;

public interface ReservacionService {


    public List<ReservacionDto> listar();
    public ReservacionContent listarPagSort(int pageNo, int pageSize, String ordernarPor, String sortDir);
    public ReservacionDto obtenerReservacionPorId(Long Id);
    public boolean existePorId(long id);

    public ReservacionDto crear(ReservacionDto reservacionDto);
    public ReservacionDto actualizarReservacion(ReservacionDto reservacionDto, long id);
    public void eliminarReservacion(long id);
}
