package co.edu.poligran.crudproductos.service.ordenamiento;

import co.edu.poligran.crudproductos.model.Producto;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class OrdenPorNombre implements EstrategiaOrdenamiento {

    @Override
    public String clave() {
        return "nombre";
    }

    @Override
    public Comparator<Producto> comparador() {
        return Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER);
    }
}
