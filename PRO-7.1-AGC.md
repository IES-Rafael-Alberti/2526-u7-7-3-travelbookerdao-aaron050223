## Respuestas (CE 7.c, 7.d y 7.e)

### [CE 7.c] ¿Qué librería/clases has utilizado para realizar la práctica? Métodos más interesantes

- `java.io.File` para la persistencia, en [ReservaDAOFichero.kt](src/main/kotlin/es/iesra/datos/ReservaDAOFichero.kt).
- `java.time.LocalDateTime` para registrar la fecha de creación de reservas, en [Reserva.kt](src/main/kotlin/es/iesra/dominio/Reserva.kt).

### [CE 7.d]

#### 2.a ¿Qué formato le has dado a la información del fichero para guardar y recuperar la información?

He usado formato CSV, teniendo una reserva por línea y en el primer campo indico el tipo.

Ejemplos:

- Hotel: `Hotel,id,fechaCreacion,descripcion,ubicacion,numeroNoches`
- Vuelo: `Vuelo,id,fechaCreacion,descripcion,origen,destino,horaVuelo`

Ejemplo: [ReservaDAOFichero.kt](src/main/kotlin/es/iesra/datos/ReservaDAOFichero.kt).

#### 2.b ¿Qué estrategia has usado para trabajar con los ficheros?

Estrategia aplicada:

- Ruta relativa: `datos/fichero.csv` en [TravelBooker.kt](src/main/kotlin/TravelBooker.kt).
- En `dao.leer()` recojo los datos del fichero e instacio las clases con dichos datos, para asi registrar las reservas realizadas, en [ReservaRepository.kt](src/main/kotlin/es/iesra/datos/ReservaRepository.kt).
- Si el fichero no existe, `leer` devuelve lista vacía.

#### 2.c ¿Cómo se gestionan los errores?

Se hace una validación en [ReservaRepository.kt](src/main/kotlin/es/iesra/datos/ReservaRepository.kt) donde se comprueba si ya existe esa reserva en la lista. Si es asi, no se añadira de nuevo.

### [CE 7.e]

#### 3.a Describe la forma de acceso para leer información

- Compruebo que el fichero existe (`if (!fichero.exists()) return lista`).
- Recorro línea a línea con `forEachLine`.
- Separo por columnas (`split(",")`).
- Vuelo a instanciar según el tipo (`Hotel`/`Vuelo`) con `creaInstancia`.

Ejemplo: [ReservaDAOFichero.kt](src/main/kotlin/es/iesra/datos/ReservaDAOFichero.kt).

#### 3.b Describe la forma de acceso para escribir información

- Se pasa cada objeto a una cadena CSV.
- Se escribe con `appendText(linea)`.
- El repositorio usa primero DAO y luego mantiene copia en memoria.

Ejemplo: [ReservaDAOFichero.kt](src/main/kotlin/es/iesra/datos/ReservaDAOFichero.kt) y [ReservaRepository.kt](src/main/kotlin/es/iesra/datos/ReservaRepository.kt).

#### 3.c Describe la forma de acceso para actualizar información

Mediante los métodos `eliminar()` y `agregar()`.

**Método `agregar(reserva)`** en [ReservaRepository.kt](src/main/kotlin/es/iesra/datos/ReservaRepository.kt):
- Comprueba si la reserva ya existe en la lista con `contains`.
- Si no existe, la guarda en el DAO y en memoria.
- Retorna `true` si se agregó, `false` si ya existía (evita duplicados).

**Método `eliminar(tipo, id)`** en [ReservaRepository.kt](src/main/kotlin/es/iesra/datos/ReservaRepository.kt):
- Recibe el tipo de reserva (`VUELO` o `HOTEL`) y el id.
- Llama a `dao.borrar(tipo, id)` para eliminar del fichero.
- Luego elimina de la lista en memoria usando `removeIf` filtrando por tipo e id.
- Retorna `true` si se eliminó, `false` si no habia reserva con esos datos.

Ejemplo:

[IReservaRepository.kt](src/main/kotlin/es/iesra/datos/IReservaRepository.kt), [ReservaRepository.kt](src/main/kotlin/es/iesra/datos/ReservaRepository.kt), [ReservaDAOFichero.kt](src/main/kotlin/es/iesra/datos/ReservaDAOFichero.kt)