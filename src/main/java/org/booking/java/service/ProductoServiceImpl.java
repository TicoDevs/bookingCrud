package org.booking.java.service;

import org.booking.java.domain.Producto;
import org.booking.java.dto.ProductoContent;
import org.booking.java.dto.ProductoDto;
import org.booking.java.exception.ResourceNotFoundException;
import org.booking.java.repository.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl  implements ProductoService{

    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ProductoDto> listar() {
        List<Producto> productoList = productoRepository.findAll();
        List<ProductoDto> productoDtoList = productoList.stream().map(usuario -> mappearDTO(usuario)).collect(Collectors.toList());
        return productoDtoList;
    }

    @Override
    public ProductoContent listarPagSort(int pageNo, int pageSize, String ordernarPor, String sortDir) {
        Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordernarPor).ascending():Sort.by(ordernarPor).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Producto> productos = productoRepository.findAll(pageable);
        List<Producto> productoList = productos.getContent();
        List<ProductoDto> contenido = productoList.stream().map(producto -> mappearDTO(producto))
                .collect(Collectors.toList());
        ProductoContent productoContent = new ProductoContent();
        productoContent.setNumeroPaginas(productos.getNumber());
        productoContent.setMedidaPagina(productos.getSize());
        productoContent.setTotalElementos(productos.getTotalElements());
        productoContent.setTotalPaginas(productos.getTotalPages());
        productoContent.setUltima(productos.isLast());
        productoContent.setContenido(contenido);
        return productoContent;
    }

    @Override
    public ProductoDto obtenerProductoPorId(Long Id) {
        Producto producto =  productoRepository.findById(Id)
                .orElseThrow(() ->new ResourceNotFoundException("Producto","Id", Id));
        return mappearDTO(producto);
    }

    @Override
    public boolean existePorId(long id) {
        return productoRepository.existsById(id);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return productoRepository.existsByNombre(nombre);
    }

    @Override
    public ProductoDto crear(ProductoDto productoDto) {
        //Convertimos de DTO a entidad
        Producto producto = mappearEntidad(productoDto);
        Producto nueva = productoRepository.save(producto);
        //Convertimos de entidad a DTO
        ProductoDto productoDtoRepuesta = mappearDTO(nueva);
        return productoDtoRepuesta;
    }

    @Override
    public ProductoDto actualizarProducto(ProductoDto productoDto, long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "Id", id));
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setCantidad(productoDto.getCantidad());
        producto.setEstado(productoDto.getEstado());
        producto.setImg(productoDto.getImg());
        producto.setCodigo(productoDto.getCodigo());
        Producto productoActualizada = productoRepository.save(producto);
        return mappearDTO(productoActualizada);
    }

    @Override
    public void eliminarProducto(long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "Id", id));
        productoRepository.delete(producto);
    }

    //Convierte entidad a DTO
    private ProductoDto mappearDTO (Producto producto){
        ProductoDto productoDto = modelMapper.map(producto, ProductoDto.class);
        return productoDto;
    }

    //Convierte DTO a entidad
    private Producto mappearEntidad (ProductoDto productoDto){
        Producto producto = modelMapper.map(productoDto, Producto.class);
        return producto;
    }
}
