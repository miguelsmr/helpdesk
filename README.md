# HelpDesk: gestión de incidencias

Aplicación de consola en Java para registrar, consultar y cerrar incidencias del centro, con pruebas automáticas y persistencia en un archivo de texto.

Práctica evaluable 1, módulo DWES (2º DAW).

## Requisitos

- Java 17 o superior
- Maven (incluido en IntelliJ IDEA)

## Cómo ejecutar

**Desde IntelliJ:** abrir `AplicacionHelpDesk.java` y pulsar el triángulo verde junto a `main`.

**Desde la terminal**, en la carpeta raíz del proyecto:

    mvn compile
    java -cp target/classes org.endes.AplicacionHelpDesk

Hay que ejecutarlo desde la raíz para que `tickets.txt` se lea y se guarde ahí.

## Cómo ejecutar las pruebas

    mvn test

Resultado esperado: 26 pruebas, BUILD SUCCESS.

## Estructura y responsabilidades

| Clase | Responsabilidad | 
|---|---|
| `Ticket` | Una incidencia: identificador, descripción y estado. Valida sus datos al crearse. |
| `GestorTickets` | Guarda las incidencias en memoria: crear, buscar, listar, contar y cargar. |
| `ArchivoTickets` | Lee y escribe las incidencias en `tickets.txt`. |
| `AplicacionHelpDesk` | Menú de consola. Solo interactúa con el usuario. |

## Menú

1. Crear incidencia
2. Listar incidencias
3. Buscar incidencia por identificador
4. Cerrar incidencia
5. Mostrar estadísticas
6. Guardar incidencias
0. Salir

## Formato de `tickets.txt`

Una incidencia por línea, con tres campos separados por punto y coma:

    id;cerrado;descripcion

Ejemplo:

    1;false;Falla el teclado
    2;true;Sin conexión a Internet

- `id`: entero positivo, sin repetir.
- `cerrado`: exactamente `true` o `false`.
- `descripcion`: texto de una línea; puede contener `;`.

Al arrancar, se valida el archivo completo. Si alguna línea es inválida, el programa muestra el número de línea y se detiene sin cargar nada ni sobrescribir el archivo.

## Decisiones de diseño

- Un intento de crear una incidencia inválida no consume identificador ni altera la colección.
- `listar()` devuelve una copia de la lista, para que nadie modifique la colección interna desde fuera.
- Los identificadores nuevos continúan desde el mayor cargado del archivo.

## Limitaciones conocidas

- No se guarda automáticamente: hay que usar la opción 6 antes de salir.
- El guardado no es atómico: si el programa se interrumpe mientras guarda, el archivo podría quedar incompleto.
- Una línea en blanco en `tickets.txt` se considera inválida.
- No se pueden crear descripciones de varias líneas.

## Autor

Miguel Luzón Prado