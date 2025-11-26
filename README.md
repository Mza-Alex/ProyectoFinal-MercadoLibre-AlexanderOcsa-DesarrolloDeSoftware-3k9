🧬 **API Detector de Mutantes – Proyecto Final MercadoLibre**
--------------------------------------------
📌 **Alumno**

Estudiante:	Alexander Gabriel Ocsa

Legajo:	51436

Curso:	3K9

Materia: Desarrollo de Software

Año: 2025
__________________________________________
 **Objetivo del Proyecto**
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

🏗 Arquitectura del Proyecto
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

 **Algoritmo de Detección**
----------------------------------
El algoritmo detecta secuencias de 4 caracteres iguales revisando: Horizontal, vertical o diagonal.

Termina anticipadamente cuando encuentra más de una secuencia válida, cumpliendo el requisito de eficiencia.

 Endpoints Disponibles
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
___________
Instalación y Ejecución Local
-----------------
Prerrequisitos
Java Development Kit (JDK): 
Versión 17 o superior.

Git: Para clonar el repositorio.

1- Clonar el repositorio: https://github.com/Mza-Alex/ProyectoFinal-MercadoLibre-AlexanderOcsa-DesarrolloDeSoftware-3k9.git

2- Compilar y ejecutar la aplicación

Como se usa el wrapper de Gradle:

 En windows: gradlew.bat bootRun

En linux: ./gradlew bootRun

_________
 Tests del Proyecto
---------
El proyecto incluye una suite completa de tests unitarios y de integración para garantizar el correcto funcionamiento de cada capa.

**MutantServiceTest:** Guarda humano/mutante, evita duplicados, llama al detector una sola vez.

**MutantControllerTest:** Respuestas 200/403, manejo de JSON inválido o vacío.

**StatsControllerTest:** DEvuelve correctamente el JSON de estadísticas usando mocks.

**DnaValidatorTest:** Valida matriz NxN, caracteres válidos y longitudes correctas.

**MutantDetectorTest:** Detecta secuencias mutantes en horizontal, vertical y diagonal.

Estos test son ejecutados con .\gradlew.bat clean test (Windows), ./gradlew clean test (Mac/Linux), o gradlew test en cualquier OS
_________

 Forma para usar la API (Endpoints)
---------

Con el programa corriendo, para ver que fincione correctamente accedemos a los siguientes sitios:

### En URL local:
    Swagger UI:   http://localhost:8080
    Base de Datos local: http://localhost:8080/h2-console

### URL Remota(Render):
    Swagger UI: https://mutantes-api-z661.onrender.com/swagger-ui/index.html
    Base de Datos: https://mutantes-api-z661.onrender.com/h2-console

Para el ingreso a la base de datos colocar:


JDBC URL ->	jdbc:h2:mem:testdb

User ->	sa

Password ->	(vacío)