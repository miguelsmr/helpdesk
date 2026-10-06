package org.endes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EstadisticasTest {
    private GestorTickets gestor;

    @BeforeEach
    void prepararGestor() {
        gestor = new GestorTickets();
    }

    @Test
    void gestorVacioTieneTodasLasCuentasACero() {
        assertEquals(0, gestor.contarTotal());
        assertEquals(0, gestor.contarAbiertos());
        assertEquals(0, gestor.contarCerrados());
    }

    @Test
    void dosIncidenciasAbiertas() {
        gestor.crear("Falla el teclado");
        gestor.crear("Sin conexión a Internet");

        assertEquals(2, gestor.contarTotal());
        assertEquals(2, gestor.contarAbiertos());
        assertEquals(0, gestor.contarCerrados());
    }

    @Test
    void dosIncidenciasConUnaCerrada() {
        gestor.crear("Falla el teclado");
        Ticket segundo = gestor.crear("Sin conexión a Internet");
        segundo.cerrar();

        assertEquals(2, gestor.contarTotal());
        assertEquals(1, gestor.contarAbiertos());
        assertEquals(1, gestor.contarCerrados());
    }
}
