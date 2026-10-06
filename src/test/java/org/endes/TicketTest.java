package org.endes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicketTest {

    @Test
    void ticketNuevoEmpiezaAbierto() {
        Ticket ticket = new Ticket(1, "Falla el teclado");

        assertFalse(ticket.estaCerrado());
    }

    @Test
    void cerrarCambiaElEstadoACerrado() {
        Ticket ticket = new Ticket(1, "Falla el teclado");

        ticket.cerrar();

        assertTrue(ticket.estaCerrado());
    }

    @Test
    void cerrarDosVecesDejaElTicketCerrado() {
        Ticket ticket = new Ticket(1, "Falla el teclado");

        ticket.cerrar();
        ticket.cerrar();

        assertTrue(ticket.estaCerrado());
    }

    @Test
    void rechazaDescripcionEnBlanco() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, "   "));
    }

    @Test
    void rechazaDescripcionNula() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(1, null));
    }

    @Test
    void rechazaIdentificadorCero() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(0, "Falla el teclado"));
    }

    @Test
    void rechazaIdentificadorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(-3, "Falla el teclado"));
    }

    @Test
    void conservaIdentificadorYDescripcion() {
        Ticket ticket = new Ticket(7, "Sin conexión a Internet");

        assertEquals(7, ticket.getId());
        assertEquals("Sin conexión a Internet", ticket.getDescripcion());
    }
}
