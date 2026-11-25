# ProyectoSoftware: CRUD de Productos

Aplicación web CRUD para la entidad **Producto**, construida con arquitectura en capas como proyecto de aula del Politécnico Grancolombiano.

**Integrantes:** Brayan Lopez, Santiago Caballero y Felipe Torres.

**Video de la arquitectura y el funcionamiento:** https://youtu.be/6ypilFEHCm4

## Tecnologías

| Herramienta | Uso |
|---|---|
| Java 21 y Spring Boot 4 | Framework de la aplicación |
| Spring Data JPA (Hibernate) | ORM y acceso a datos |
| PostgreSQL 17 | Base de datos |
| springdoc-openapi (Swagger UI) | Documentación de la API |
| Docker y Docker Compose | Contenedores |
| Postman | Pruebas de la API |
| SonarQube Community Build | Análisis de calidad del código |

## Estructura

```
src/main/java/co/edu/poligran/crudproductos/
├── config/        Configuración de OpenAPI
├── controller/    Capa de presentación (REST)
├── dto/           Objetos de entrada y salida de la API
├── exception/     Excepciones y manejador global de errores
├── mapper/        Conversión entre DTO y entidad
├── model/         Entidad Producto
├── repository/    Capa de persistencia (Spring Data JPA)
└── service/       Capa de negocio
    └── ordenamiento/  Estrategias de orden (patrón Strategy)
```

## Cómo ejecutarlo

1. Copiar `.env.example` como `.env` y escribir un usuario y una contraseña para la base de datos.
2. Construir e iniciar los contenedores:

   ```bash
   docker compose up -d --build
   ```

3. Abrir:
   - Aplicación web: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - Descripción OpenAPI: http://localhost:8080/v3/api-docs

Para detenerla: `docker compose down`.

## Endpoints

| Método | Ruta | Acción |
|---|---|---|
| GET | /api/v1/productos?orden=precio&direccion=desc | Lista los productos ordenados (orden: id, nombre, precio o stock; direccion: asc o desc) |
| GET | /api/v1/productos/{id} | Consulta un producto |
| POST | /api/v1/productos | Crea un producto |
| PUT | /api/v1/productos/{id} | Actualiza un producto |
| DELETE | /api/v1/productos/{id} | Elimina un producto |

## Patrón de diseño: Strategy

El listado de productos se puede ordenar por id, nombre, precio o stock. Cada criterio es una clase que implementa `EstrategiaOrdenamiento` y `SelectorOrdenamiento` elige la estrategia según el parámetro `orden` de la petición. Para agregar un criterio nuevo basta con crear otra clase anotada con `@Component`; el servicio y el controlador no cambian.

## Diagramas

Los diagramas de clases y de arquitectura están en `docs/diagramas/`.

## Pruebas

- Pruebas unitarias: `./mvnw test`
- Pruebas de la API con Postman: importar los archivos de la carpeta `postman/` y ejecutar la colección con el entorno `local`.

## Análisis con SonarQube

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
./mvnw clean verify sonar:sonar -Dsonar.projectKey=crud-productos -Dsonar.host.url=http://localhost:9000 -Dsonar.token=TOKEN_GENERADO
```

## Ramas

- `main`: versiones estables, una por entrega.
- `develop`: integración del trabajo.
- `feature/*`: una rama por tarea, que llega a `develop` mediante pull request.
