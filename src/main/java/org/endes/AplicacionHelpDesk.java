package org.endes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class AplicacionHelpDesk {
    private final Scanner scanner;
    private final GestorTickets gestor;
    private final ArchivoTickets archivo;

    public AplicacionHelpDesk(Scanner scanner, GestorTickets gestor, ArchivoTickets archivo) {
        this.scanner = scanner;
        this.gestor = gestor;
        this.archivo = archivo;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorTickets gestor = new GestorTickets();
        ArchivoTickets archivo = new ArchivoTickets(Path.of("tickets.txt"));

        AplicacionHelpDesk aplicacion = new AplicacionHelpDesk(scanner, gestor, archivo);
        if (aplicacion.cargarIncidencias()) {
            aplicacion.ejecutar();
        }
    }

    public boolean cargarIncidencias() {
        try {
            gestor.cargar(archivo.cargar());
            System.out.println("Incidencias cargadas: " + gestor.contarTotal());
            return true;
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de incidencias: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("El archivo de incidencias no es válido. " + e.getMessage());
        }
        System.out.println("El programa se detiene para no sobrescribir el archivo.");
        return false;
    }

    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1 -> crearIncidencia();
                case 2 -> listarIncidencias();
                case 3 -> buscarIncidencia();
                case 4 -> cerrarIncidencia();
                case 5 -> mostrarEstadisticas();
                case 6 -> guardarIncidencias();
                case 0 -> {
                    salir = true;
                    System.out.println("Hasta pronto.");
                }
                default -> System.out.println("Opción no válida. Elige una opción del menú.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("HELPDESK DEL CENTRO");
        System.out.println();
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar incidencias");
        System.out.println("3. Buscar incidencia por identificador");
        System.out.println("4. Cerrar incidencia");
        System.out.println("5. Mostrar estadísticas");
        System.out.println("6. Guardar incidencias");
        System.out.println("0. Salir (guarda antes con la opción 6)");
    }

    private void crearIncidencia() {
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        try {
            Ticket ticket = gestor.crear(descripcion);
            System.out.println("Incidencia creada con el identificador " + ticket.getId() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("No se ha creado la incidencia: " + e.getMessage());
        }
    }

    private void listarIncidencias() {
        List<Ticket> incidencias = gestor.listar();
        if (incidencias.isEmpty()) {
            System.out.println("No hay incidencias registradas.");
            return;
        }
        for (Ticket ticket : incidencias) {
            System.out.println(ticket.resumen());
        }
    }

    private void buscarIncidencia() {
        int id = leerEntero("Identificador a buscar: ");
        Ticket ticket = gestor.buscar(id);
        if (ticket == null) {
            System.out.println("No existe ninguna incidencia con el identificador " + id + ".");
        } else {
            System.out.println(ticket.resumen());
        }
    }

    private void cerrarIncidencia() {
        int id = leerEntero("Identificador a cerrar: ");
        Ticket ticket = gestor.buscar(id);
        if (ticket == null) {
            System.out.println("No existe ninguna incidencia con el identificador " + id + ".");
        } else if (ticket.estaCerrado()) {
            System.out.println("La incidencia " + id + " ya estaba cerrada.");
        } else {
            ticket.cerrar();
            System.out.println("Incidencia " + id + " cerrada correctamente.");
        }
    }

    private void mostrarEstadisticas() {
        System.out.println("Total de incidencias: " + gestor.contarTotal());
        System.out.println("Abiertas: " + gestor.contarAbiertos());
        System.out.println("Cerradas: " + gestor.contarCerrados());
    }

    private void guardarIncidencias() {
        try {
            archivo.guardar(gestor.listar());
            System.out.println("Incidencias guardadas correctamente: " + gestor.contarTotal());
        } catch (IOException e) {
            System.out.println("No se han podido guardar las incidencias: " + e.getMessage());
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Introduce un número entero.");
            }
        }
    }
}
