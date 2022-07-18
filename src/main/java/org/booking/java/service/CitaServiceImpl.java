package org.booking.java.service;

import org.booking.java.domain.Cita;
import org.booking.java.dto.CitaContent;
import org.booking.java.dto.CitaDto;
import org.booking.java.exception.ResourceNotFoundException;
import org.booking.java.repository.CitaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaServiceImpl implements CitaService{
    @Autowired
    CitaRepository citaRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<CitaDto> listar() {
        List<Cita> citaList = citaRepository.findAll();
        List<CitaDto> citaDtoList = citaList.stream().map(cita -> mappearDTO(cita)).collect(Collectors.toList());
        return citaDtoList;
    }

    @Override
    public CitaContent listarPagSort(int pageNo, int pageSize, String ordernarPor, String sortDir) {
        Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordernarPor).ascending():Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Cita> citas = citaRepository.findAll(pageable);
        List<Cita> citaList = citas.getContent();
        List<CitaDto> contenido = citaList.stream().map(cita -> mappearDTO(cita))
                .collect(Collectors.toList());
        CitaContent citaContent = new CitaContent();
        citaContent.setNumeroPaginas(citas.getNumber());
        citaContent.setMedidaPagina(citas.getSize());
        citaContent.setTotalElementos(citas.getTotalElements());
        citaContent.setTotalPaginas(citas.getTotalPages());
        citaContent.setUltima(citas.isLast());
        citaContent.setContenido(contenido);
        return citaContent;
    }

    @Override
    public CitaDto obtenerCitaPorId(Long Id) {
        Cita cita =  citaRepository.findById(Id)
                .orElseThrow(() ->new ResourceNotFoundException("Cita","Id", Id));
        return mappearDTO(cita);
    }

    @Override
    public boolean existePorId(long id) {
        return citaRepository.existsById(id);
    }

    @Override
    public CitaDto crear(CitaDto citaDto) {
        //Convertimos de DTO a entidad
        Cita cita = mappearEntidad(citaDto);
        Cita nueva = citaRepository.save(cita);
        //Convertimos de entidad a DTO
        CitaDto citaDtoRepuesta = mappearDTO(nueva);
        return citaDtoRepuesta;
    }

    @Override
    public CitaDto actualizarCita(CitaDto citaDto, long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita", "Id", id));
        cita.setCita(citaDto.getCita());
        cita.setEstado(citaDto.isEstado());
        cita.setTipo(citaDto.getTipo());
        Cita citaActualizada = citaRepository.save(cita);
        return mappearDTO(citaActualizada);

    }

    @Override
    public void eliminarCita(long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita", "Id", id));
        citaRepository.delete(cita);
    }

    //Convierte entidad a DTO
    private CitaDto mappearDTO (Cita cita){
        CitaDto citaDto = modelMapper.map(cita, CitaDto.class);
        return citaDto;
    }

    //Convierte DTO a entidad
    private Cita mappearEntidad (CitaDto citaDto){
        Cita cita = modelMapper.map(citaDto, Cita.class);
        return cita;
    }
}
