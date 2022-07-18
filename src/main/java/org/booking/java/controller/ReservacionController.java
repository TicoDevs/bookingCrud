package org.booking.java.controller;

import org.booking.java.dto.*;
import org.booking.java.dto.Mensaje;
import org.booking.java.dto.ReservacionContent;
import org.booking.java.dto.ReservacionDto;
import org.booking.java.repository.ReservacionRepository;
import org.booking.java.dto.CitaDto;
import org.booking.java.dto.ProductoDto;
import org.booking.java.service.CitaService;
import org.booking.java.service.ProductoService;
import org.booking.java.service.ReservacionService;
import org.booking.java.service.ReservacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.booking.java.constantes.AppConstantes.*;

@RestController
@RequestMapping("/api/reservaciones")
public class ReservacionController {

    @Autowired
    ReservacionService reservacionService;

    @Autowired
    ProductoService productoService;
    @Autowired
    CitaService citaService;

    @GetMapping("/listarPageSort")
    public ResponseEntity<ProductoContent> listarProductosContent(
            @RequestParam(value = "pageNo", defaultValue = NUMERO_PAGINA_DEFECTO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = MEDIDA_PAGINA_DEFECTO, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue = ORDENAR_DIRECCCION_DEFECTO,required = false) String sortDir){
        return new ResponseEntity(reservacionService.listarPagSort(pageNo, pageSize, ordernarPor, sortDir), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ReservacionDto>> listar(){
        List<ReservacionDto> list = reservacionService.listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservacionDto> obtenerReservacionPorId(@PathVariable(name = "id")long id){
        return ResponseEntity.ok(reservacionService.obtenerReservacionPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody ReservacionDto reservacionDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campo incorrecto"), HttpStatus.BAD_REQUEST);
        ReservacionDto reservacion = new ReservacionDto(reservacionDto.getFechaReservacion(), reservacionDto.getEstado(), reservacionDto.getTipo(), reservacionDto.getIdMiembro(), reservacionDto.getIdProducto(), reservacionDto.getIdCita());

        //reservacion.setFechaReservacion(reservacionDto.getFechaReservacion());
        //reservacion.setEstado(reservacionDto.getEstado());
        //reservacion.setTipo(reservacionDto.getTipo());
        //reservacion.setIdMiembro(reservacionDto.getIdMiembro());
        //reservacion.setIdProducto(reservacion.getIdProducto());
        //reservacion.setIdCita(reservacion.getIdCita());

        reservacionService.crear(reservacion);
        return new ResponseEntity(new Mensaje("Reservación guardada"), HttpStatus.CREATED);
    }

    @PostMapping("/crearReservacionCita")
    public ResponseEntity<?> crearReservacionCita(@Valid @RequestBody ReservacionDto reservacionDto, @Valid @RequestBody CitaDto citaDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campo incorrecto"), HttpStatus.BAD_REQUEST);
        CitaDto reservacionCita = new CitaDto(citaDto.getCita(), citaDto.isEstado(), citaDto.getTipo());

        ReservacionDto reservacion = new ReservacionDto(reservacionDto.getFechaReservacion(), reservacionDto.getEstado(), reservacionDto.getTipo(), reservacionDto.getIdMiembro(), reservacionDto.getIdProducto(), reservacionDto.getIdCita());

        this.citaService.crear(reservacionCita);


        reservacionService.crear(reservacion);
        return new ResponseEntity(new Mensaje("Reservación guardada"), HttpStatus.CREATED);
    }

    @PostMapping("/crearReservacionProducto")
    public ResponseEntity<?> crearReservacionProducto(@Valid @RequestBody ReservacionDto reservacionDto, @Valid @RequestBody ProductoDto productoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campo incorrecto"), HttpStatus.BAD_REQUEST);
        ProductoDto reservacionProducto = new ProductoDto(productoDto.getNombre(), productoDto.getDescripcion(), productoDto.getCantidad(), productoDto.getEstado(), productoDto.getImg() ,productoDto.getCodigo());

        ReservacionDto reservacion = new ReservacionDto(reservacionDto.getFechaReservacion(), reservacionDto.getEstado(), reservacionDto.getTipo(), reservacionDto.getIdMiembro(), reservacionDto.getIdProducto(), reservacionDto.getIdCita());

        this.productoService.crear(reservacionProducto);


        reservacionService.crear(reservacion);
        return new ResponseEntity(new Mensaje("Reservación guardada"), HttpStatus.CREATED);
    }


    @PutMapping("/modificar/{id}")
    public ResponseEntity<ReservacionDto> actualizarUsuario(@Valid @RequestBody ReservacionDto nuevo, @PathVariable(name = "id")long id){
        if(!this.reservacionService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        ReservacionDto reservacionDtoRespuesta = reservacionService.actualizarReservacion(nuevo,id);
        return  new ResponseEntity<>(reservacionDtoRespuesta, HttpStatus.OK);
    }

    @PutMapping("/cambiaEstadoReservacion/{id}")
    public ResponseEntity<ReservacionDto> cambiaEstadoReservacion(@Valid @RequestBody ReservacionDto nuevo, @PathVariable(name = "id")long id){
        if(!this.reservacionService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        ReservacionDto reservacionDtoRespuesta = reservacionService.actualizarReservacion(nuevo,id);
        return  new ResponseEntity<>(reservacionDtoRespuesta, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarReservacion(@PathVariable(name = "id")long id){
        reservacionService.eliminarReservacion(id);
        return new ResponseEntity<>("Reserva eliminada con exito", HttpStatus.OK);
    }



}
