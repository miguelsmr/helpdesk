# Evidencias

| Archivo | Qué muestra |
|---|---|
| `01-tests-ticket.png` | Pruebas de `Ticket` en verde: 8 pruebas, BUILD SUCCESS (commit 1) |
| `02-tests-gestor.png` | Pruebas de `GestorTickets` y estadísticas en verde: 20 pruebas (commit 2) |
| `03-tests-archivo.png` | Pruebas de persistencia en verde: 26 pruebas (commit 3) |
| `04a-demo-arranque-y-creacion.png` | Demo, primera ejecución: arranque sin datos y creación de dos incidencias |
| `04b-demo-descripcion-vacia-y-cierre.png` | Demo: descripción vacía rechazada y cierre de la incidencia 2 |
| `04c-demo-estadisticas-y-listado.png` | Demo: estadísticas (total 2, abiertas 1, cerradas 1) y listado |
| `04d-demo-guardado-y-salida.png` | Demo: guardado de 2 incidencias y salida |
| `05a-demo-reinicio-recuperacion.png` | Demo, segunda ejecución: se recuperan 2 incidencias y la nueva recibe el identificador 3 |
| `05b-demo-reinicio-guardado.png` | Demo: guardado de 3 incidencias y salida |
| `06-test-fallido.png` | Prueba que falla a propósito (BUILD FAILURE) |
| `07-tests-final.png` | 26 pruebas en verde tras revertir el fallo |
| `08-git-log.png` | Historial de commits subido a GitHub |
| `09a-prueba-manual-crear-y-listar.png` | Primera prueba manual del menú: crear y listar una incidencia |
| `09b-prueba-manual-guardar-y-salir.png` | Primera prueba manual: guardar y salir |

## Prueba fallida provocada

**Qué cambié:** en `GestorTickets.crear`, incrementé `siguienteId` antes de construir el `Ticket`, de modo que el identificador se consume aunque la descripción sea inválida.

**Qué prueba falló:** `creacionInvalidaNoAlteraLaColeccionNiElContador` (`expected: <2> but was: <3>`).

**Por qué falló:** la prueba crea una incidencia, intenta crear otra con descripción en blanco (lanza excepción) y espera que la siguiente válida reciba el id 2. Con el cambio, el intento fallido ya había consumido el id 2, así que la siguiente recibió el 3. La prueba comprueba que una creación fallida no altera el estado del gestor.

**Cómo lo corregí:** deshice el cambio con `git checkout` y volví a ejecutar las pruebas: 26 en verde (`07-tests-final.png`). No se hizo commit del código roto.
