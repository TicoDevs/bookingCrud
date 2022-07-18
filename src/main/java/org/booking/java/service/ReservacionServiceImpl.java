package org.booking.java.service;

import org.booking.java.domain.Reservacion;
import org.booking.java.dto.ReservacionContent;
import org.booking.java.dto.ReservacionDto;
import org.booking.java.exception.ResourceNotFoundException;
import org.booking.java.repository.ReservacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservacionServiceImpl implements ReservacionService{


    @Autowired
    ReservacionRepository reservacionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<ReservacionDto> listar() {
        List<Reservacion> reservacionList = reservacionRepository.findAll();
        List<ReservacionDto> reservacionDtoList = reservacionList.stream().map(usuario -> mappearEntidad(usuario)).collect(Collectors.toList());
        return reservacionDtoList;
    }

    @Override
    public ReservacionContent listarPagSort(int pageNo, int pageSize, String ordernarPor, String sortDir) {
        Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordernarPor).ascending():Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Reservacion> reservaciones = reservacionRepository.findAll(pageable);
        List<Reservacion> reservacionList = reservaciones.getContent();
        List<ReservacionDto> contenido = reservacionList.stream().map(reservacion -> mappearEntidad(reservacion))
                .collect(Collectors.toList());
        ReservacionContent reservacionContent = new ReservacionContent();
        reservacionContent.setNumeroPaginas(reservaciones.getNumber());
        reservacionContent.setMedidaPagina(reservaciones.getSize());
        reservacionContent.setTotalElementos(reservaciones.getTotalElements());
        reservacionContent.setTotalPaginas(reservaciones.getTotalPages());
        reservacionContent.setUltima(reservaciones.isLast());
        reservacionContent.setContenido(contenido);
        return reservacionContent;
    }



    @Override
    public ReservacionDto obtenerReservacionPorId(Long Id) {
        Reservacion reservacion =  reservacionRepository.findById(Id)
                .orElseThrow(() ->new ResourceNotFoundException("Reservacion","Id", Id));
        return mappearDTO(reservacion);
    }

    @Override
    public boolean existePorId(long id) {
        return reservacionRepository.existsById(id);
    }


    @Override
    public ReservacionDto crear(ReservacionDto reservacionDto) {
        //Convertimos de DTO a entidad
        Reservacion reservacion = mappearEntidad(reservacionDto);
        Reservacion nueva = reservacionRepository.save(reservacion);
        //Convertimos de entidad a DTO
        ReservacionDto reservacionDtoRespuesta = mappearDTO(nueva);
        return reservacionDtoRespuesta;
    }

    @Override
    public ReservacionDto actualizarReservacion(ReservacionDto reservacionDto, long id) {

        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservacion", "Id", id));
        reservacion.setFechaReservacion(reservacionDto.getFechaReservacion());
        reservacion.setEstado(reservacionDto.getEstado());
        reservacion.setTipo(reservacionDto.getTipo());
        reservacion.setIdMiembro(reservacionDto.getIdMiembro());
        reservacion.setIdProducto(reservacionDto.getIdProducto());
        reservacion.setIdCita(reservacionDto.getIdCita());

        Reservacion reservacionActualizada = reservacionRepository.save(reservacion);
        return mappearDTO(reservacionActualizada);
    }

    @Override
    public void eliminarReservacion(long id) {
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservacion", "Id", id));
        reservacionRepository.delete(reservacion);

    }
    private ReservacionDto mappearDTO (Reservacion reservacion){
        ReservacionDto reservacionDto = modelMapper.map(reservacion, ReservacionDto.class);
        return reservacionDto;
    }
    private ReservacionDto mappearEntidad(Reservacion reservacion) {
        ReservacionDto reservacionDto = modelMapper.map(reservacion, ReservacionDto.class);
        return reservacionDto;
    }
}
