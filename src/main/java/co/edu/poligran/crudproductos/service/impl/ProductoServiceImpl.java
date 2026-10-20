package co.edu.poligran.crudproductos.service.impl;

import co.edu.poligran.crudproductos.dto.ProductoRequest;
import co.edu.poligran.crudproductos.dto.ProductoResponse;
import co.edu.poligran.crudproductos.exception.ProductoDuplicadoException;
import co.edu.poligran.crudproductos.exception.RecursoNoEncontradoException;
import co.edu.poligran.crudproductos.mapper.ProductoMapper;
import co.edu.poligran.crudproductos.model.Producto;
import co.edu.poligran.crudproductos.repository.ProductoRepository;
import co.edu.poligran.crudproductos.service.ProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar() {
        return productoRepository.findAll().stream()
                .map(productoMapper::aRespuesta)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse buscarPorId(Long id) {
        return productoMapper.aRespuesta(obtener(id));
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        if (productoRepository.existsByNombreIgnoreCase(request.nombre().trim())) {
            throw new ProductoDuplicadoException(request.nombre());
        }
        Producto guardado = productoRepository.save(productoMapper.aEntidad(request));
        return productoMapper.aRespuesta(guardado);
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = obtener(id);
        String nuevoNombre = request.nombre().trim();
        boolean cambiaNombre = !producto.getNombre().equalsIgnoreCase(nuevoNombre);
        if (cambiaNombre && productoRepository.existsByNombreIgnoreCase(nuevoNombre)) {
            throw new ProductoDuplicadoException(nuevoNombre);
        }
        productoMapper.copiarDatos(request, producto);
        return productoMapper.aRespuesta(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.delete(obtener(id));
    }

    private Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un producto con id " + id));
    }
}
