<div align="center">

# 📚 Library Management

**Sistema de gestión de biblioteca** desarrollado con **Java 21** y **Spring Boot 3.5**, aplicando **Arquitectura Hexagonal**, **Domain-Driven Design (DDD)**, **Clean Code** y principios **SOLID**.

Expone una **API REST** desacoplada para gestionar libros, préstamos, devoluciones, multas y usuarios.

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)](https://swagger.io/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

</div>

---

## 📑 Tabla de contenidos

- [✨ Características](#-características)
- [🏗️ Arquitectura](#️-arquitectura)
- [🛠️ Tecnologías](#️-tecnologías)
- [📂 Estructura del proyecto](#-estructura-del-proyecto)
- [✅ Requisitos previos](#-requisitos-previos)
- [⚙️ Configuración](#️-configuración)
- [🚀 Instalación y ejecución](#-instalación-y-ejecución)
- [🔌 Endpoints principales](#-endpoints-principales)
- [🔐 Roles y seguridad](#-roles-y-seguridad)
- [📘 Documentación de la API (Swagger)](#-documentación-de-la-api-swagger)
- [🧪 Tests](#-tests)
- [📄 Licencia](#-licencia)

---

## ✨ Características

- Gestión de **libros** y sus ejemplares (copias).
- Gestión de **préstamos**: creación, devolución y simulación de mora.
- Gestión de **multas**: consulta y pago.
- Gestión de **usuarios** y roles (`USER`, `LIBRARIAN`, `ADMIN`).
- **Autenticación** basada en JWT.
- Migraciones de base de datos versionadas con **Flyway**.

---

## 🏗️ Arquitectura

El proyecto sigue una **Arquitectura Hexagonal (Ports & Adapters)** combinada con DDD, separando claramente:

- **domain**: modelo de dominio (entidades, value objects, excepciones, repositorios como puertos, servicios de dominio). No depende de ningún framework.
- **application**: casos de uso (`usecase`) que orquestan la lógica de negocio, y configuración de aplicación.
- **infrastructure**: adaptadores de entrada/salida — controladores REST, seguridad (JWT), persistencia (JPA, mappers, entidades), y configuración técnica.

Esto permite que la lógica de negocio (dominio y casos de uso) permanezca independiente de detalles como la base de datos o el framework web.

---

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.5** (Web, Data JPA, Security, Validation)
- **PostgreSQL** como base de datos
- **Flyway** para migraciones de esquema
- **JWT (jjwt)** para autenticación
- **MapStruct** para mapeo entre capas
- **Lombok** para reducir boilerplate
- **JaCoCo** para cobertura de tests
- **springdoc-openapi (Swagger UI)** para documentación de la API
- **Maven** como gestor de dependencias

---

## 📂 Estructura del proyecto

```
src/main/java/pe/com/apolo/
├── domain/                 # Modelo de dominio, value objects, repositorios (puertos), excepciones
├── application/
│   └── usecase/            # Casos de uso: book, fine, loan, login, user
└── infrastructure/
    ├── persistence/         # Adaptadores JPA: entidades, mappers, repositorios
    ├── security/            # Configuración de seguridad y JWT
    └── web/
        ├── controller/      # Controladores REST
        ├── dto/             # DTOs de request/response
        ├── mapper/          # Mapeo entre DTOs y modelo de dominio
        └── exception/       # Manejo de excepciones HTTP

src/main/resources/
├── application.yml
└── db/migration/            # Scripts de Flyway (V1, V2, ...)
```

---

## ✅ Requisitos previos

- JDK 21
- Maven (o usar el wrapper `mvnw` incluido)
- PostgreSQL en ejecución

---

## ⚙️ Configuración

La aplicación se configura mediante variables de entorno (ver `src/main/resources/application.yml`):

| Variable        | Descripción                                   |
|-----------------|------------------------------------------------|
| `DATABASE_NAME` | Nombre de la base de datos PostgreSQL          |
| `PASSWORD_DB`   | Contraseña del usuario `postgres`              |
| `JWT_SECRET`    | Clave secreta usada para firmar los tokens JWT |

Ejemplo (Linux/macOS):

```bash
export DATABASE_NAME=library_db
export PASSWORD_DB=tu_password
export JWT_SECRET=tu_clave_secreta
```

> La base de datos debe existir previamente en PostgreSQL (por defecto se conecta a `localhost:5432`); Flyway se encarga de crear el esquema y las tablas al arrancar la aplicación.

---

## 🚀 Instalación y ejecución

```bash
# Clonar el repositorio
git clone https://github.com/AlejandroPoloJ/library-management.git
cd library-management

# Compilar y ejecutar tests
./mvnw clean verify

# Ejecutar la aplicación
./mvnw spring-boot:run
```

Al iniciar, Flyway ejecutará las migraciones (`V1__create_schema.sql`, `V2__insert_admin_user.sql`), creando el esquema y un usuario administrador semilla (`admin@apolo.com`) para poder autenticarte desde el primer arranque.

---

## 🔌 Endpoints principales

Todos los endpoints están bajo el prefijo `/api/v1`.

| Recurso | Método | Ruta                                | Descripción                        |
|---------|--------|--------------------------------------|-------------------------------------|
| Auth    | POST   | `/auth/login`                       | Autenticación y obtención de JWT   |
| Books   | POST   | `/books`                            | Crear libro                        |
| Books   | GET    | `/books/{id}`                       | Obtener libro por id                |
| Books   | GET    | `/books`                            | Listar libros                       |
| Books   | POST   | `/books/{id}/copies`                | Agregar ejemplares a un libro        |
| Loans   | POST   | `/loans`                            | Registrar préstamo                  |
| Loans   | POST   | `/loans/{loanId}/return`            | Registrar devolución                |
| Loans   | GET    | `/loans/users/{userId}`             | Listar préstamos de un usuario      |
| Loans   | POST   | `/loans/{loanId}/simulate-overdue/{days}` | Simular mora (uso administrativo) |
| Fines   | GET    | `/fines/users/{userId}`             | Listar multas de un usuario         |
| Fines   | POST   | `/fines/{fineId}/pay`               | Pagar una multa                     |
| Users   | POST   | `/users`                            | Registrar usuario                   |
| Users   | GET    | `/users/{id}`                       | Obtener usuario por id              |
| Users   | GET    | `/users`                            | Listar usuarios                     |
| Users   | PATCH  | `/users/{id}/role`                  | Cambiar rol de un usuario            |

---

## 🔐 Roles y seguridad

La autenticación se realiza mediante JWT. Existen tres roles: `USER`, `LIBRARIAN` y `ADMIN`. El acceso a los endpoints está protegido con `@PreAuthorize`, por ejemplo:

- Crear/editar libros y préstamos: `LIBRARIAN` o `ADMIN`.
- Consultar libros y préstamos propios: cualquier usuario autenticado.
- Cambiar el rol de un usuario: solo `ADMIN`.
- Registro de usuario: acceso público.

---

## 📘 Documentación de la API (Swagger)

La API está documentada con **OpenAPI** usando **springdoc-openapi**. Con la aplicación en ejecución, podés acceder a:

| Recurso                | URL                                   |
|-------------------------|----------------------------------------|
| Swagger UI              | `http://localhost:8080/swagger-ui.html` |
| Especificación OpenAPI  | `http://localhost:8080/v3/api-docs`     |

Los endpoints protegidos requieren un JWT. En Swagger UI, hacé clic en **Authorize** e ingresá el token obtenido en `POST /api/v1/auth/login` con el formato `Bearer <token>`.

---

## 🧪 Tests

El proyecto incluye pruebas unitarias para el dominio, casos de uso, adaptadores de persistencia, seguridad y controladores web. Para ejecutarlas junto con el reporte de cobertura (JaCoCo):

```bash
./mvnw clean verify
```

El reporte de cobertura se genera en `target/site/jacoco/index.html`.

---

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**. Esto significa que cualquier persona puede usar, copiar, modificar y distribuir el código libremente, incluso con fines comerciales, siempre que se mantenga el aviso de copyright original. El software se ofrece "tal cual", sin garantías.

Consulta el archivo [LICENSE](LICENSE) para el texto completo.

<div align="center">

Hecho con ☕ y Spring Boot por [Alejandro Polo](https://github.com/AlejandroPoloJ)

</div>