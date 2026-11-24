package co.edu.poligran.crudproductos.service.ordenamiento;

import co.edu.poligran.crudproductos.exception.CriterioOrdenInvalidoException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Elige la estrategia de ordenamiento a partir de la clave que llega en la petición.
 * Spring inyecta todas las implementaciones de {@link EstrategiaOrdenamiento}, así que
 * para agregar un criterio nuevo basta con crear otra clase anotada con @Component.
 */
@Component
public class SelectorOrdenamiento {

    private final Map<String, EstrategiaOrdenamiento> estrategias;

    public SelectorOrdenamiento(List<EstrategiaOrdenamiento> estrategias) {
        this.estrategias = estrategias.stream()
                .collect(Collectors.toMap(EstrategiaOrdenamiento::clave, Function.identity(),
                        (a, b) -> a, TreeMap::new));
    }

    public EstrategiaOrdenamiento obtener(String clave) {
        String normalizada = clave == null ? "id" : clave.trim().toLowerCase(Locale.ROOT);
        EstrategiaOrdenamiento estrategia = estrategias.get(normalizada);
        if (estrategia == null) {
            throw new CriterioOrdenInvalidoException(clave, estrategias.keySet());
        }
        return estrategia;
    }
}
