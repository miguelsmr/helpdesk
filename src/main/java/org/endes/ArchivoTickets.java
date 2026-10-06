package org.endes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArchivoTickets {
    private final Path ruta;

    public ArchivoTickets(Path ruta) {
        this.ruta = ruta;
    }

    public List<Ticket> cargar() throws IOException {
        List<Ticket> tickets = new ArrayList<>();
        if (!Files.exists(ruta)) {
            return tickets;
        }

        List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
        Set<Integer> identificadores = new HashSet<>();
        for (int i = 0; i < lineas.size(); i++) {
            int numeroLinea = i + 1;
            Ticket ticket = interpretarLinea(lineas.get(i), numeroLinea);
            if (!identificadores.add(ticket.getId())) {
                throw new IllegalArgumentException(
                        "Línea " + numeroLinea + ": identificador repetido (" + ticket.getId() + ").");
            }
            tickets.add(ticket);
        }
        return tickets;
    }

    public void guardar(List<Ticket> tickets) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Ticket ticket : tickets) {
            lineas.add(ticket.getId() + ";" + ticket.estaCerrado() + ";" + ticket.getDescripcion());
        }
        // Files.write crea el archivo si no existe y sustituye su contenido si ya existe.
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }

    private Ticket interpretarLinea(String linea, int numeroLinea) {
        // El límite 3 hace que la descripción conserve los puntos y coma que contenga.
        String[] partes = linea.split(";", 3);
        if (partes.length != 3) {
            throw new IllegalArgumentException(
                    "Línea " + numeroLinea + ": formato incorrecto (se esperaba id;estado;descripción).");
        }

        int id;
        try {
            id = Integer.parseInt(partes[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": el identificador no es numérico.");
        }

        boolean cerrado;
        if (partes[1].equals("true")) {
            cerrado = true;
        } else if (partes[1].equals("false")) {
            cerrado = false;
        } else {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": el estado debe ser true o false.");
        }

        Ticket ticket;
        try {
            ticket = new Ticket(id, partes[2]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Línea " + numeroLinea + ": " + e.getMessage());
        }
        if (cerrado) {
            ticket.cerrar();
        }
        return ticket;
    }
}