package co.edu.poligran.crudproductos.service;

import co.edu.poligran.crudproductos.dto.ProductoRequest;
import co.edu.poligran.crudproductos.exception.ProductoDuplicadoException;
import co.edu.poligran.crudproductos.exception.RecursoNoEncontradoException;
import co.edu.poligran.crudproductos.mapper.ProductoMapper;
import co.edu.poligran.crudproductos.repository.ProductoRepository;
import co.edu.poligran.crudproductos.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoServiceImpl(productoRepository, new ProductoMapper());
    }

    @Test
    void crearConNombreRepetidoLanzaExcepcion() {
        ProductoRequest request = new ProductoRequest("Teclado", "USB", "Accesorios", new BigDecimal("50000"), 10);
        when(productoRepository.existsByNombreIgnoreCase("Teclado")).thenReturn(true);

        assertThatThrownBy(() -> productoService.crear(request))
                .isInstanceOf(ProductoDuplicadoException.class);
        verify(productoRepository, never()).save(any());
    }

    @Test
    void buscarProductoInexistenteLanzaExcepcion() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productoService.buscarPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }
}
