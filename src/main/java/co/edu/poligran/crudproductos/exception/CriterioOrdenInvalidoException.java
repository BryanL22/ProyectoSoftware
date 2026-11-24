package co.edu.poligran.crudproductos.exception;

import java.util.Set;

public class CriterioOrdenInvalidoException extends RuntimeException {

    public CriterioOrdenInvalidoException(String criterio, Set<String> permitidos) {
        super("El criterio de orden '" + criterio + "' no es válido. Valores permitidos: "
                + String.join(", ", permitidos));
    }
}
