package co.edu.poligran.crudproductos.service.ordenamiento;

import co.edu.poligran.crudproductos.model.Producto;

import java.util.Comparator;

/**
 * Estrategia (patrón Strategy, GoF) que define un criterio para ordenar productos.
 * Cada implementación encapsula un criterio distinto y se puede cambiar en tiempo
 * de ejecución sin modificar el servicio que la usa.
 */
public interface EstrategiaOrdenamiento {

    /** Valor del parámetro "orden" que activa esta estrategia (por ejemplo, "precio"). */
    String clave();

    /** Comparador que define el orden ascendente del criterio. */
    Comparator<Producto> comparador();
}
