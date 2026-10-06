package org.endes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorTickets {
    private final List<Ticket> tickets = new ArrayList<>();
    private int siguienteId = 1;

    public Ticket crear(String descripcion) {
        // Si la descripción no es válida, el constructor lanza una excepción
        // y no se llega ni a añadir el ticket ni a consumir el identificador.
        Ticket nuevo = new Ticket(siguienteId, descripcion);
        tickets.add(nuevo);
        siguienteId++;
        return nuevo;
    }

    public Ticket buscar(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    public List<Ticket> listar() {
        // Copia de la lista, pero con los mismos objetos Ticket dentro.
        return new ArrayList<>(tickets);
    }

    public int contarTotal() {
        return tickets.size();
    }

    public int contarAbiertos() {
        int abiertos = 0;
        for (Ticket ticket : tickets) {
            if (!ticket.estaCerrado()) {
                abiertos++;
            }
        }
        return abiertos;
    }

    public int contarCerrados() {
        return contarTotal() - contarAbiertos();
    }

    public void cargar(List<Ticket> recuperados) {
        // Primero se valida todo; solo si es correcto se modifica el gestor.
        Set<Integer> identificadores = new HashSet<>();
        int mayor = 0;
        for (Ticket ticket : recuperados) {
            if (!identificadores.add(ticket.getId())) {
                throw new IllegalArgumentException("Identificador repetido: " + ticket.getId());
            }
            mayor = Math.max(mayor, ticket.getId());
        }
        tickets.clear();
        tickets.addAll(recuperados);
        siguienteId = mayor + 1;
    }
}