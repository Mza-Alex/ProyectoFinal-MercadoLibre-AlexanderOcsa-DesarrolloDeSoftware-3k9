🧬 **API Detector de Mutantes – Proyecto Final MercadoLibre**
--------------------------------------------
📌 **Alumno**

Estudiante:	Alexander Gabriel Ocsa

Legajo:	51436

Curso:	3K9

Materia: Desarrollo de Software

Año: 2025
__________________________________________
🎯 **Objetivo del Proyecto**
-------------------------------------------
Este proyecto implementa una API REST en Spring Boot capaz de determinar si un ADN corresponde a un mutante según el desafío técnico de MercadoLibre.

Un ADN es mutante si contiene más de una secuencia de cuatro letras idénticas (A, T, C o G) en forma:

-Horizontal

-Vertical

-Diagonal o Diagonal Inversa

La API expone dos endpoints:

POST /mutant → Determina si un ADN es mutante

GET /stats → Devuelve estadísticas globales

Además, cada ADN se almacena en una base de datos H2 utilizando un hash SHA-256 para evitar procesamientos duplicados.

🏗️ Arquitectura del Proyecto
------------------------------------------
El proyecto utiliza una arquitectura en capas clara y mantenible:

### controller
    -MutantController
    -StatsController

### service
    -MutantService
    -StatsService

### repository
    -DnaRecordRepository

### entity
    -DnaRecord
### dto
[java](src/test/java)    -DnaRequest
    -StatsResponse
### validator
    -DnaValidator

**Resumen de responsabilidades:**

**Controller:**  Manejo de endpoints HTTP

**Service:**  Lógica de negocio (detección y estadísticas)

**Repository:**  Acceso a datos (JPA)

**Entity:**  Tabla de ADN en H2

**DTO:**  Entrada y salida JSON

**Validator:** Valida la entrada del usuario (sí tiene formato correcto)

🧪 **Algoritmo de Detección**
----------------------------------
El algoritmo detecta secuencias de 4 caracteres iguales revisando: Horizontal, vertical o diagonal.

Termina anticipadamente cuando encuentra más de una secuencia válida, cumpliendo el requisito de eficiencia.

🚀 Endpoints Disponibles
---------------------------------
🔹 POST /mutant

Determina si un ADN es mutante.

Body esperado
{
"dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

### Respuestas

    200 OK	ADN mutante
    403 Forbidden	ADN humano
    400 Bad Request	JSON inválido
🔹 GET /stats

Devuelve estadísticas del sistema.

Respuesta
{
"count_mutant_dna": 10,
"count_human_dna": 20,
"ratio": 0.5
}
_________
🧪 Tests del Proyecto
---------
El proyecto incluye una suite completa de tests unitarios y de integración para garantizar el correcto funcionamiento de cada capa.

**MutantServiceTest:** Guarda humano/mutante, evita duplicados, llama al detector una sola vez.

**MutantControllerTest:** Respuestas 200/403, manejo de JSON inválido o vacío.

**StatsControllerTest:** DEvuelve correctamente el JSON de estadísticas usando mocks.

**DnaValidatorTest:** Valida matriz NxN, caracteres válidos y longitudes correctas.

**MutantDetectorTest:** Detecta secuencias mutantes en horizontal, vertical y diagonal.
_________

💾 Base de Datos (H2)
---------

La aplicación utiliza a H2 como base de datos en memoria.

Consola H2
http://localhost:8080/h2-console

Credenciales


JDBC URL ->	jdbc:h2:mem:testdb

User ->	sa

Password ->	(vacío)

🐳 **Docker - Ejecución con Contenedor**

### Construir imagen
    docker build -t mutantes-api .

#### Ejecutar contenedor: 
    docker run -p 8080:8080 mutantes-api


La API responderá en:

👉 http://localhost:8080

### ▶ Cómo Ejecutar Localmente (sin Docker)
    ./gradlew bootRun

### ✔ Proyecto Listo para Deploy

Cuando completes el deploy, podés agregar acá:

Recurso	URL
Render Deployment	(pendiente)