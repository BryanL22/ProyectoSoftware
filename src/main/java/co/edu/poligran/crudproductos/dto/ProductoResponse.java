package co.edu.poligran.crudproductos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Datos de un producto que devuelve la API. */
public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        String categoria,
        BigDecimal precio,
        Integer stock,
        Boolean activo,
        LocalDateTime fechaCreacion
) {
}
