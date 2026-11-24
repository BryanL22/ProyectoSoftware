package co.edu.poligran.crudproductos.service;

import co.edu.poligran.crudproductos.dto.ProductoRequest;
import co.edu.poligran.crudproductos.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {

    /**
     * Lista los productos ordenados según el criterio indicado.
     *
     * @param orden       clave de la estrategia: id, nombre, precio o stock
     * @param descendente true para invertir el orden
     */
    List<ProductoResponse> listar(String orden, boolean descendente);

    ProductoResponse buscarPorId(Long id);

    ProductoResponse crear(ProductoRequest request);

    ProductoResponse actualizar(Long id, ProductoRequest request);

    void eliminar(Long id);
}
