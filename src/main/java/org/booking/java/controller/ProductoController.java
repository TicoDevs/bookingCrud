package org.booking.java.controller;

import org.booking.java.domain.Producto;
import org.booking.java.dto.Mensaje;
import org.booking.java.dto.ProductoContent;
import org.booking.java.dto.ProductoDto;
import org.booking.java.repository.ProductoRepository;
import org.booking.java.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.booking.java.constantes.AppConstantes.*;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/listarPageSort")
    public ResponseEntity<ProductoContent> listarProductosContent(
            @RequestParam(value = "pageNo", defaultValue = NUMERO_PAGINA_DEFECTO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = MEDIDA_PAGINA_DEFECTO, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ORDENAR_POR_DEFECTO, required = false) String ordernarPor,
            @RequestParam(value = "sortDir", defaultValue = ORDENAR_DIRECCCION_DEFECTO,required = false) String sortDir){
        return new ResponseEntity(productoService.listarPagSort(pageNo, pageSize, ordernarPor, sortDir), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoDto>> listar(){
        List<ProductoDto> list = productoService.listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable(name = "id")long id){
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoDto productoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puesto"), HttpStatus.BAD_REQUEST);
        ProductoDto producto = new ProductoDto(productoDto.getNombre(),productoDto.getDescripcion(),productoDto.getCantidad(), productoDto.getEstado(),productoDto.getImg(),productoDto.getCodigo());

        productoService.crear(producto);
        return new ResponseEntity(new Mensaje("Producto guardado"), HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ProductoDto> actualizarUsuario(@Valid @RequestBody ProductoDto nuevo, @PathVariable(name = "id")long id){
        if(!this.productoService.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        ProductoDto productoDtoRespuesta = productoService.actualizarProducto(nuevo,id);
        return  new ResponseEntity<>(productoDtoRespuesta, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable(name = "id")long id){
        productoService.eliminarProducto(id);
        return new ResponseEntity<>("Producto eliminado con exito", HttpStatus.OK);
    }




}
