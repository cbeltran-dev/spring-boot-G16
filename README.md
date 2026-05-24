# spring-boot-G16

# Ejercicio — CRUD de Series
## Tutoría G16 · CodiGo by Tecsup

---

> Aplica exactamente el mismo patrón que construimos con `Pelicula` durante la sesión.
> Si algo no recuerdas, revisa la guía de referencia de anotaciones que se compartió al inicio.

---

## El modelo

Una serie de streaming tiene los siguientes campos:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Integer` | Asignado por el sistema, el cliente no lo manda |
| `titulo` | `String` | Nombre de la serie |
| `temporadas` | `Integer` | Cantidad de temporadas |
| `anio` | `Integer` | Año de estreno |

---

## Lo que debes construir

### 1. Model
Crea la clase `Serie` en el paquete `model` con sus cuatro campos, constructor vacío, constructor completo, getters y setters.

<details>
<summary>🔍 Ver solución</summary>

```java
// model/Serie.java
public class Serie {

    private Integer id;
    private String titulo;
    private Integer temporadas;
    private Integer anio;

    public Serie() {}

    public Serie(Integer id, String titulo, Integer temporadas, Integer anio) {
        this.id = id;
        this.titulo = titulo;
        this.temporadas = temporadas;
        this.anio = anio;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getTemporadas() { return temporadas; }
    public void setTemporadas(Integer temporadas) { this.temporadas = temporadas; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
}
```

</details>

---

### 2. Repository
Crea `SerieRepository` en el paquete `repository` con una lista en memoria.

Debe tener estos métodos:
- `listar()` — retorna todas las series
- `buscarPorId(Integer id)` — retorna la serie con ese id o `null` si no existe
- `guardar(Serie serie)` — asigna el id y agrega la serie a la lista
- `actualizar(Serie serie)` — busca por id en la lista y actualiza los campos
- `eliminar(Integer id)` — elimina la serie de la lista

Carga 3 series de prueba en el constructor.

<details>
<summary>🔍 Ver solución</summary>

```java
// repository/SerieRepository.java
@Repository
public class SerieRepository {

    // lista en memoria que simula la base de datos
    private final List<Serie> series = new ArrayList<>();

    // simula el autoincremento de id de una base de datos
    private Integer contadorId = 1;

    public SerieRepository() {
        series.add(new Serie(contadorId++, "Breaking Bad", 5, 2008));
        series.add(new Serie(contadorId++, "Stranger Things", 4, 2016));
        series.add(new Serie(contadorId++, "The Last of Us", 1, 2023));
    }

    // retorna todos los elementos de la lista
    public List<Serie> listar() {
        return series;
    }

    // recorre la lista buscando la serie cuyo id coincida
    // .equals() compara el valor, no la referencia en memoria
    public Serie buscarPorId(Integer id) {
        for (Serie s : series) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    // asigna el siguiente id y agrega la serie a la lista
    public Serie guardar(Serie serie) {
        serie.setId(contadorId++);
        series.add(serie);
        return serie;
    }

    // recorre la lista buscando el id del objeto recibido y actualiza sus campos
    public Serie actualizar(Serie serie) {
        for (Serie s : series) {
            if (s.getId().equals(serie.getId())) {
                s.setTitulo(serie.getTitulo());
                s.setTemporadas(serie.getTemporadas());
                s.setAnio(serie.getAnio());
                return s;
            }
        }
        return null;
    }

    // busca la serie por id y la elimina de la lista
    public void eliminar(Integer id) {
        Serie serie = buscarPorId(id);
        if (serie != null) {
            series.remove(serie);
        }
    }
}
```

</details>

---

### 3. DTOs
Crea `SerieRequestDTO` y `SerieResponseDTO` en el paquete `dto`.

- `SerieRequestDTO` — los campos que el cliente puede mandar. Sin `id`.
- `SerieResponseDTO` — los campos que el cliente recibe. Con `id` y con `mensaje`.

<details>
<summary>🔍 Ver solución</summary>

```java
// dto/SerieRequestDTO.java
public class SerieRequestDTO {

    private String titulo;
    private Integer temporadas;
    private Integer anio;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getTemporadas() { return temporadas; }
    public void setTemporadas(Integer temporadas) { this.temporadas = temporadas; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
}
```

```java
// dto/SerieResponseDTO.java
public class SerieResponseDTO {

    private Integer id;
    private String titulo;
    private Integer temporadas;
    private Integer anio;
    private String mensaje;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getTemporadas() { return temporadas; }
    public void setTemporadas(Integer temporadas) { this.temporadas = temporadas; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
```

</details>

---

### 4. Mapper
Crea `SerieMapper` en el paquete `mapper` con dos métodos estáticos:
- `toModel(SerieRequestDTO dto)` — convierte el RequestDTO al Model
- `toResponse(Serie serie, String mensaje)` — convierte el Model al ResponseDTO

<details>
<summary>🔍 Ver solución</summary>

```java
// mapper/SerieMapper.java
public class SerieMapper {

    // convierte el DTO de entrada al Model
    // el id no se setea acá — el Repository se lo asigna al guardar
    public static Serie toModel(SerieRequestDTO dto) {
        Serie serie = new Serie();
        serie.setTitulo(dto.getTitulo());
        serie.setTemporadas(dto.getTemporadas());
        serie.setAnio(dto.getAnio());
        return serie;
    }

    // convierte el Model al DTO de salida
    // el mensaje lo decide el Service — el Mapper solo estructura el objeto
    public static SerieResponseDTO toResponse(Serie serie, String mensaje) {
        SerieResponseDTO response = new SerieResponseDTO();
        response.setId(serie.getId());
        response.setTitulo(serie.getTitulo());
        response.setTemporadas(serie.getTemporadas());
        response.setAnio(serie.getAnio());
        response.setMensaje(mensaje);
        return response;
    }
}
```

</details>

---

### 5. Service
Crea `SerieService` en el paquete `service` con estos métodos:

| Método | Retorna | Descripción |
|---|---|---|
| `listar()` | `List<SerieResponseDTO>` | Retorna todas las series como ResponseDTO |
| `buscarPorId(Integer id)` | `SerieResponseDTO` | Retorna la serie o `null` si no existe |
| `buscarPorTitulo(String titulo)` | `List<SerieResponseDTO>` | Filtra por título sin distinguir mayúsculas |
| `guardar(SerieRequestDTO dto)` | `SerieResponseDTO` | Guarda y retorna la serie creada |
| `actualizar(Integer id, SerieRequestDTO dto)` | `SerieResponseDTO` | Actualiza y retorna la serie o `null` si no existe |
| `eliminar(Integer id)` | `void` | Elimina la serie |

<details>
<summary>🔍 Ver solución</summary>

```java
// service/SerieService.java
@Service
public class SerieService {

    private final SerieRepository serieRepository;

    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    // recorre la lista y convierte cada serie a ResponseDTO
    public List<SerieResponseDTO> listar() {
        List<SerieResponseDTO> resultado = new ArrayList<>();
        for (Serie s : serieRepository.listar()) {
            resultado.add(SerieMapper.toResponse(s, "Serie listada correctamente"));
        }
        return resultado;
    }

    public SerieResponseDTO buscarPorId(Integer id) {
        Serie serie = serieRepository.buscarPorId(id);
        if (serie == null) {
            return null;
        }
        return SerieMapper.toResponse(serie, "Serie obtenida correctamente");
    }

    // recorre la lista y agrega al resultado solo las que coincidan con el titulo
    public List<SerieResponseDTO> buscarPorTitulo(String titulo) {
        List<SerieResponseDTO> resultado = new ArrayList<>();
        for (Serie s : serieRepository.listar()) {
            if (s.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(SerieMapper.toResponse(s, "Serie encontrada"));
            }
        }
        return resultado;
    }

    public SerieResponseDTO guardar(SerieRequestDTO dto) {
        Serie serie = SerieMapper.toModel(dto);
        Serie guardada = serieRepository.guardar(serie);
        return SerieMapper.toResponse(guardada, "Serie creada correctamente");
    }

    public SerieResponseDTO actualizar(Integer id, SerieRequestDTO dto) {
        Serie existente = serieRepository.buscarPorId(id);
        if (existente == null) {
            return null;
        }
        Serie serieActualizar = SerieMapper.toModel(dto);
        serieActualizar.setId(id);
        Serie actualizada = serieRepository.actualizar(serieActualizar);
        return SerieMapper.toResponse(actualizada, "Serie actualizada correctamente");
    }

    public void eliminar(Integer id) {
        serieRepository.eliminar(id);
    }
}
```

</details>

---

### 6. Controller
Crea `SerieController` en el paquete `controller` con estos endpoints:

| Método | URL | Descripción |
|---|---|---|
| `GET` | `/api/series` | Lista todas las series |
| `GET` | `/api/series/{id}` | Busca una serie por id |
| `GET` | `/api/series/buscar?titulo=` | Filtra series por título |
| `POST` | `/api/series` | Crea una serie nueva |
| `PUT` | `/api/series/{id}` | Actualiza una serie existente |
| `DELETE` | `/api/series/{id}` | Elimina una serie |

Todos los endpoints deben usar `ResponseEntity` con el código HTTP correcto:
- `GET` → `200 OK`
- `POST` → `201 Created`
- `PUT` → `200 OK`
- `DELETE` → `204 No Content`
- Recurso no encontrado → `404 Not Found`

<details>
<summary>🔍 Ver solución</summary>

```java
// controller/SerieController.java
@RestController
@RequestMapping("/api/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping
    public ResponseEntity<List<SerieResponseDTO>> listar() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serieService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SerieResponseDTO> buscarPorId(@PathVariable Integer id) {
        SerieResponseDTO response = serieService.buscarPorId(id);
        if (response == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<SerieResponseDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serieService.buscarPorTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<SerieResponseDTO> guardar(@RequestBody SerieRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serieService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SerieResponseDTO> actualizar(
            @PathVariable Integer id,
            @RequestBody SerieRequestDTO dto) {
        SerieResponseDTO response = serieService.actualizar(id, dto);
        if (response == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        serieService.eliminar(id);
    }
}
```

</details>

---

## Prueba en Postman

Una vez que tengas todo construido, prueba este flujo en orden:

**1. Listar series iniciales**
```
GET http://localhost:8080/api/series
```

**2. Crear una serie nueva**
```
POST http://localhost:8080/api/series
```
```json
{
  "titulo": "House of the Dragon",
  "temporadas": 2,
  "anio": 2022
}
```

**3. Buscar por id**
```
GET http://localhost:8080/api/series/4
```

**4. Buscar por título**
```
GET http://localhost:8080/api/series/buscar?titulo=dragon
```

**5. Actualizar**
```
PUT http://localhost:8080/api/series/4
```
```json
{
  "titulo": "House of the Dragon",
  "temporadas": 3,
  "anio": 2022
}
```

**6. Eliminar**
```
DELETE http://localhost:8080/api/series/4
```

**7. Confirmar eliminación**
```
GET http://localhost:8080/api/series/4
```
Debe responder `404 Not Found`.

---

*Tutoría G16 · CodiGo by Tecsup*
