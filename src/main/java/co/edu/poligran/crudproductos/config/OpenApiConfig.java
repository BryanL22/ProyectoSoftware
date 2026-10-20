package co.edu.poligran.crudproductos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiProductos() {
        return new OpenAPI().info(new Info()
                .title("API CRUD de Productos")
                .version("v1")
                .description("Servicio REST para crear, consultar, actualizar y eliminar productos."));
    }
}
