package org.endes;

public class Ticket {
    private final int id;
    private final String descripcion;
    private boolean cerrado;

    public Ticket(int id, String descripcion) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser nula ni estar en blanco.");
        }
        if (descripcion.contains("\n") || descripcion.contains("\r")) {
            throw new IllegalArgumentException("La descripción debe ocupar una sola línea.");
        }
        this.id = id;
        this.descripcion = descripcion;
        this.cerrado = false;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean estaCerrado() {
        return cerrado;
    }

    public void cerrar() {
        this.cerrado = true;
    }

    public String resumen() {
        String estado = cerrado ? "CERRADA" : "ABIERTA";
        return "ID: " + id + " | Estado: " + estado + " | Descripción: " + descripcion;
    }
}