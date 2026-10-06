package org.endes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArchivoTicketsTest {

    @TempDir
    Path carpeta;

    @Test
    void archivoInexistenteDevuelveUnaListaVacia() throws IOException {
        ArchivoTickets archivo = new ArchivoTickets(carpeta.resolve("no-existe.txt"));

        assertTrue(archivo.cargar().isEmpty());
    }

    @Test
    void guardarYCargarConservaIdentificadoresDescripcionesYEstados() throws IOException {
        Ticket abierto = new Ticket(1, "Falla el teclado");
        Ticket cerrado = new Ticket(2, "Sin conexión; revisar el router");
        cerrado.cerrar();
        ArchivoTickets archivo = new ArchivoTickets(carpeta.resolve("tickets.txt"));

        archivo.guardar(List.of(abierto, cerrado));
        List<Ticket> recuperados = archivo.cargar();

        assertEquals(2, recuperados.size());
        assertEquals(1, recuperados.get(0).getId());
        assertEquals("Falla el teclado", recuperados.get(0).getDescripcion());
        assertFalse(recuperados.get(0).estaCerrado());
        assertEquals(2, recuperados.get(1).getId());
        assertEquals("Sin conexión; revisar el router", recuperados.get(1).getDescripcion());
        assertTrue(recuperados.get(1).estaCerrado());
    }

    @Test
    void guardarSustituyeElContenidoAnterior() throws IOException {
        Path ruta = carpeta.resolve("tickets.txt");
        Files.write(ruta, List.of("1;false;Uno", "2;false;Dos", "3;false;Tres"));
        ArchivoTickets archivo = new ArchivoTickets(ruta);

        archivo.guardar(List.of(new Ticket(1, "Solo esta")));

        assertEquals(List.of("1;false;Solo esta"), Files.readAllLines(ruta));
    }

    @Test
    void rechazaIdentificadoresRepetidos() throws IOException {
        Path ruta = carpeta.resolve("tickets.txt");
        Files.write(ruta, List.of("1;false;Uno", "1;true;Repetido"));
        ArchivoTickets archivo = new ArchivoTickets(ruta);

        assertThrows(IllegalArgumentException.class, archivo::cargar);
    }

    @Test
    void rechazaUnEstadoQueNoEsTrueNiFalse() throws IOException {
        Path ruta = carpeta.resolve("tickets.txt");
        Files.write(ruta, List.of("1;quizas;Uno"));
        ArchivoTickets archivo = new ArchivoTickets(ruta);

        assertThrows(IllegalArgumentException.class, archivo::cargar);
    }

    @Test
    void unaLineaInvalidaImpideLaCargaAunqueLasAnterioresSeanValidas() throws IOException {
        Path ruta = carpeta.resolve("tickets.txt");
        Files.write(ruta, List.of("1;false;Valida", "esto no es una incidencia"));
        ArchivoTickets archivo = new ArchivoTickets(ruta);

        assertThrows(IllegalArgumentException.class, archivo::cargar);
    }
}
