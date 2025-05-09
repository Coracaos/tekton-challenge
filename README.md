# Reto Técnico Tekton Labs - API de Cálculo con Porcentaje 

## Descripción

API REST desarrollada en **Spring Boot (Java 21)** que permite calcular la suma de dos números aplicando un porcentaje adicional obtenido desde un servicio externo (mock). Además, guarda un historial de llamadas de forma asíncrona en una base de datos PostgreSQL y cachea el porcentaje durante 30 minutos para mejorar el rendimiento y tolerancia a fallos.

---

## Endpoints disponibles

### 1. `GET /api/calculate`

Suma `num1` + `num2` y adiciona un porcentaje obtenido desde un servicio externo (mock).

| Parámetro | Tipo       | Obligatorio | Descripción                                                                 |
|-----------|------------|-------------|-----------------------------------------------------------------------------|
| num1      | `decimal`  | ✅           | Primer número (entre 0.00 y 1,000,000.00, máx. 2 decimales)                 |
| num2      | `decimal`  | ✅           | Segundo número (entre 0.00 y 1,000,000.00, máx. 2 decimales)                |

📌 Si el servicio externo de porcentaje falla y no hay un valor en caché, se devuelve error 500.  
📌 El porcentaje se obtiene desde `/api/mock/percentage` y se cachea por 30 minutos.

---

### 2. `GET /api/metadata/requests`

Devuelve el historial de llamadas a la API, con paginación.

| Parámetro | Tipo     | Obligatorio | Descripción                                     |
|-----------|----------|-------------|-------------------------------------------------|
| page      | `int`    | ✅           | Número de página (mínimo 1)                     |
| size      | `int`    | ✅           | Tamaño de página (mínimo 1, máximo 50)          |

📌 El historial incluye fecha, endpoint, parámetros, y respuesta o error.  
📌 El registro de historial es asíncrono para no afectar el rendimiento.

---

### 3. `GET /api/mock/percentage`

Devuelve un porcentaje fijo (mockeado). Usado internamente por `/calculate`.

📌 Ejemplo de respuesta: `0.12` (12 %)

---

## Documentación
Accede a la documentación Swagger una vez el servicio esté corriendo:

http://localhost:8080/swagger-ui.html

## Colección Postman 

Puedes obtener la siguiente colección de Postman para probar los endpotins de la API.

[Obtener colección de Postman](https://github.com/coracaos/tekton-challenge/blob/main/postman/Tekton_Challenge.postman_collection.json)

## Requisitos

- Docker
- Docker Compose

## Cómo ejecutar el proyecto

1) Obtener repositorio del proyecto
```bash
git clone https://github.com/Coracaos/tekton-challenge.git
cd tekton-challenge
```
2) Ejecutar el proyecto con docker compose
```bash
docker compose up
```
