package co.edu.poligran.crudproductos.service.ordenamiento;

import co.edu.poligran.crudproductos.model.Producto;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class OrdenPorPrecio implements EstrategiaOrdenamiento {

    @Override
    public String clave() {
        return "precio";
    }

    @Override
    public Comparator<Producto> comparador() {
        return Comparator.comparing(Producto::getPrecio);
    }
}
