package co.edu.poligran.crudproductos.service;

import co.edu.poligran.crudproductos.dto.ProductoRequest;
import co.edu.poligran.crudproductos.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {

    List<ProductoResponse> listar();

    ProductoResponse buscarPorId(Long id);

    ProductoResponse crear(ProductoRequest request);

    ProductoResponse actualizar(Long id, ProductoRequest request);

    void eliminar(Long id);
}
