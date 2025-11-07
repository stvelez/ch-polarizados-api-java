# ch_polarizados_api

Pequeña API Spring Boot para la actividad.

## Requisitos

- JDK 17+ (o la versión configurada en el proyecto)
- Maven
- (Opcional) MySQL remoto para pruebas
- App React corriendo en http://localhost:3000

## Archivos modificados

- `src/main/java/com/example/chpolarizadosapi/ChPolarizadosApiApplication.java` — paquete corregido.
- `src/main/java/com/example/chpolarizadosapi/config/CorsConfig.java` — configuración global de CORS que permite solicitudes desde `http://localhost:3000`.

## Ejecutar la aplicación (modo rápido)

1. Construir el JAR:

```bash
mvn -DskipTests clean package
```

2. Ejecutar:

```bash
java -jar target/ch-polarizados-api-0.0.1-SNAPSHOT.jar
```

La API por defecto se expondrá en `http://localhost:8080`.

## Configurar MySQL remoto (opcional)

Edita `src/main/resources/application.properties` o usa variables de entorno. Ejemplo de configuración en `application.properties`:

```
spring.datasource.url=jdbc:mysql://mi-host-remoto:3306/mi_base_de_datos?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=mi_usuario
spring.datasource.password=mi_contraseña
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Si quieres usar variables de entorno en vez del archivo, exporta:

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://mi-host:3306/mi_db"
export SPRING_DATASOURCE_USERNAME="mi_usuario"
export SPRING_DATASOURCE_PASSWORD="mi_pass"
```
