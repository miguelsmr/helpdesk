package org.endes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestorTicketsTest {
    private GestorTickets gestor;

    @BeforeEach
    void prepararGestor() {
        gestor = new GestorTickets();
    }

    @Test
    void gestorNuevoEmpiezaVacio() {
        assertEquals(0, gestor.contarTotal());
        assertTrue(gestor.listar().isEmpty());
    }

    @Test
    void asignaIdentificadoresConsecutivos() {
        Ticket primero = gestor.crear("Falla el teclado");
        Ticket segundo = gestor.crear("Sin conexión a Internet");

        assertEquals(1, primero.getId());
        assertEquals(2, segundo.getId());
    }

    @Test
    void buscarDevuelveElMismoObjetoCreado() {
        Ticket creado = gestor.crear("Falla el teclado");

        assertSame(creado, gestor.buscar(creado.getId()));
    }

    @Test
    void buscarUnIdentificadorInexistenteDevuelveNull() {
        gestor.crear("Falla el teclado");

        assertNull(gestor.buscar(99));
    }

    @Test
    void creacionInvalidaNoAlteraLaColeccionNiElContador() {
        gestor.crear("Primera");

        assertThrows(IllegalArgumentException.class, () -> gestor.crear("   "));

        assertEquals(1, gestor.contarTotal());
        Ticket siguiente = gestor.crear("Segunda");
        assertEquals(2, siguiente.getId());
    }

    @Test
    void listarDevuelveUnaCopiaConLosMismosObjetos() {
        Ticket creado = gestor.crear("Falla el teclado");

        List<Ticket> copia = gestor.listar();

        assertSame(creado, copia.get(0));
    }

    @Test
    void modificarLaListaDevueltaNoAfectaAlGestor() {
        gestor.crear("Falla el teclado");

        gestor.listar().clear();

        assertEquals(1, gestor.contarTotal());
    }

    @Test
    void cargarContinuaLaNumeracionDesdeElMayorIdentificador() {
        gestor.cargar(List.of(new Ticket(1, "Uno"), new Ticket(5, "Cinco")));

        Ticket nuevo = gestor.crear("Nuevo");

        assertEquals(6, nuevo.getId());
    }

    @Test
    void cargarConIdentificadoresRepetidosNoAlteraElGestor() {
        gestor.crear("Existente");
        List<Ticket> repetidos = List.of(new Ticket(1, "A"), new Ticket(1, "B"));

        assertThrows(IllegalArgumentException.class, () -> gestor.cargar(repetidos));

        assertEquals(1, gestor.contarTotal());
        assertEquals("Existente", gestor.buscar(1).getDescripcion());
    }
}
