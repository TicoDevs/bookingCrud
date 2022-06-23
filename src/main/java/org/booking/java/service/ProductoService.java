package org.booking.java.service;

import org.booking.java.dto.ProductoContent;
import org.booking.java.dto.ProductoDto;

import java.util.List;

public interface ProductoService {

    public List<ProductoDto> listar();
    public ProductoContent listarPagSort(int pageNo, int pageSize, String ordernarPor, String sortDir);
    public ProductoDto obtenerProductoPorId(Long Id);
    public boolean existePorId(long id);
    public boolean existePorNombre(String nombre);
    public ProductoDto crear(ProductoDto productoDto);
    public ProductoDto actualizarProducto(ProductoDto productoDto, long id);
    public void eliminarProducto(long id);

}
