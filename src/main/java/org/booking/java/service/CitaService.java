package org.booking.java.service;

import org.booking.java.dto.CitaContent;
import org.booking.java.dto.CitaDto;

import java.util.List;

public interface CitaService {
    public List<CitaDto> listar();
    public CitaContent listarPagSort(int pageNo, int pageSize, String ordernarPor, String sortDir);
    public CitaDto obtenerCitaPorId(Long Id);
    public boolean existePorId(long id);
    public CitaDto crear( CitaDto citaDto);
    public CitaDto actualizarCita( CitaDto citaDto, long id);
    public void eliminarCita(long id);
}
