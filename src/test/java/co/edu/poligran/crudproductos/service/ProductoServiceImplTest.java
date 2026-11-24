package co.edu.poligran.crudproductos.service;

import co.edu.poligran.crudproductos.dto.ProductoRequest;
import co.edu.poligran.crudproductos.dto.ProductoResponse;
import co.edu.poligran.crudproductos.exception.CriterioOrdenInvalidoException;
import co.edu.poligran.crudproductos.model.Producto;
import co.edu.poligran.crudproductos.exception.ProductoDuplicadoException;
import co.edu.poligran.crudproductos.exception.RecursoNoEncontradoException;
import co.edu.poligran.crudproductos.mapper.ProductoMapper;
import co.edu.poligran.crudproductos.repository.ProductoRepository;
import co.edu.poligran.crudproductos.service.impl.ProductoServiceImpl;
import co.edu.poligran.crudproductos.service.ordenamiento.OrdenPorId;
import co.edu.poligran.crudproductos.service.ordenamiento.OrdenPorNombre;
import co.edu.poligran.crudproductos.service.ordenamiento.OrdenPorPrecio;
import co.edu.poligran.crudproductos.service.ordenamiento.OrdenPorStock;
import co.edu.poligran.crudproductos.service.ordenamiento.SelectorOrdenamiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        SelectorOrdenamiento selector = new SelectorOrdenamiento(List.of(
                new OrdenPorId(), new OrdenPorNombre(), new OrdenPorPrecio(), new OrdenPorStock()));
        productoService = new ProductoServiceImpl(productoRepository, new ProductoMapper(), selector);
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

    @Test
    void listarPorPrecioDescendenteUsaLaEstrategiaDePrecio() {
        when(productoRepository.findAll()).thenReturn(List.of(
                producto(1L, "Mouse", "40000", 5),
                producto(2L, "Monitor", "750000", 2),
                producto(3L, "Teclado", "185000", 12)));

        List<ProductoResponse> resultado = productoService.listar("precio", true);

        assertThat(resultado).extracting(ProductoResponse::nombre)
                .containsExactly("Monitor", "Teclado", "Mouse");
    }

    @Test
    void listarPorNombreAscendenteIgnoraMayusculas() {
        when(productoRepository.findAll()).thenReturn(List.of(
                producto(1L, "teclado", "185000", 12),
                producto(2L, "Audífonos", "90000", 7),
                producto(3L, "Mouse", "40000", 5)));

        List<ProductoResponse> resultado = productoService.listar("nombre", false);

        assertThat(resultado).extracting(ProductoResponse::nombre)
                .containsExactly("Audífonos", "Mouse", "teclado");
    }

    @Test
    void listarConCriterioDesconocidoLanzaExcepcion() {
        assertThatThrownBy(() -> productoService.listar("color", false))
                .isInstanceOf(CriterioOrdenInvalidoException.class);
    }

    private static Producto producto(Long id, String nombre, String precio, int stock) {
        Producto p = new Producto();
        p.setId(id);
        p.setNombre(nombre);
        p.setPrecio(new BigDecimal(precio));
        p.setStock(stock);
        return p;
    }
}
