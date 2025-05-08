# Reto Técnico Tekton Labs - API de Cálculo con Porcentaje 

## Descripción

API REST desarrollada en **Spring Boot (Java 21)** que permite calcular la suma de dos números aplicando un porcentaje adicional obtenido desde un servicio externo (mock). Además, guarda un historial de llamadas de forma asíncrona en una base de datos PostgreSQL y cachea el porcentaje durante 30 minutos para mejorar el rendimiento y tolerancia a fallos.

### Endpoints disponibles

| Método | Endpoint                        | Descripción                                              |
|--------|----------------------------------|----------------------------------------------------------|
| GET    | `/api/calculate`                | Suma `num1` + `num2` y aplica un porcentaje adicional    |
| GET    | `/api/metadata/requests`        | Devuelve el historial de llamadas a la API con paginación |
| GET    | `/api/mock/percentage`          | Devuelve un porcentaje fijo (simula un servicio externo) |

## Documentación
Accede a la documentación Swagger una vez el servicio esté corriendo:

http://localhost:8080/swagger-ui.html

## Requisitos

- Docker
- Docker Compose

## Cómo ejecutar el proyecto

```bash
docker compose up