package co.edu.poligran.crudproductos.mapper;

import co.edu.poligran.crudproductos.dto.ProductoRequest;
import co.edu.poligran.crudproductos.dto.ProductoResponse;
import co.edu.poligran.crudproductos.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto aEntidad(ProductoRequest r) {
        Producto p = new Producto();
        copiarDatos(r, p);
        return p;
    }

    public ProductoResponse aRespuesta(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getCategoria(),
                p.getPrecio(),
                p.getStock(),
                p.getActivo(),
                p.getFechaCreacion());
    }

    public void copiarDatos(ProductoRequest r, Producto p) {
        p.setNombre(r.nombre().trim());
        p.setDescripcion(r.descripcion());
        p.setCategoria(r.categoria());
        p.setPrecio(r.precio());
        p.setStock(r.stock());
    }
}
