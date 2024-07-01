import java.util.ArrayList;
import java.util.Scanner;

public class FisioLife {
    private Scanner scanner;
    private ArrayList<String> datosTerapeutas;
    private ArrayList<String> datosPacientes;
    private ArrayList<String> datosCitas;

    public FisioLife() {
        this.datosTerapeutas = new ArrayList<>();
        this.datosPacientes = new ArrayList<>();
        this.datosCitas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        FisioLife controlador = new FisioLife();
        controlador.iniciar();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            while (!scanner.hasNextInt()) {
                System.out.println("Error: Por favor, ingresa una opción numérica.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarTerapeuta();
                    break;
                case 2:
                    agregarPaciente();
                    break;
                case 3:
                    programarCita();
                    break;
                case 4:
                    verReporteCitas();
                    break;
                case 5:
                    verListaPersonas("Terapeuta");                    break;
                case 6:
                    verListaPersonas("Paciente");
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
            }z
        } while (opcion != 0);
    }

    //Parrilla de Menu
    private static void mostrarMenu() {
        System.out.println("\n===== Menú Principal =====");
        System.out.println("=== F i s i o - L i f e ====");
        System.out.println("============================");
        System.out.println("1. Registar Terapeuta");
        System.out.println("2. Registar Paciente");
        System.out.println("3. Programar Cita");
        System.out.println("4. Reporte de Citas");
        System.out.println("5. Lista de Terapeutas");
        System.out.println("6. Lista de Pacientes");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    // Opcion 1: agregar terapeutas
    private void agregarTerapeuta() {
        System.out.print("DNI del terapeuta: ");
        String dni = validarDNI();
        System.out.print("Nombre del terapeuta: ");
        String nombre = validarNombre();
        System.out.print("Apellido del terapeuta: ");
        String apellido = validarNombre();

        String contenido = dni + "," + nombre + "," + apellido;
        datosTerapeutas.add(contenido);

        System.out.println("Terapeuta agregado con éxito.");
    }

    // Opcion 2: agregar pacientes
    private void agregarPaciente() {
        System.out.print("DNI del paciente: ");
        String dni = validarDNI();
        System.out.print("Nombre del paciente: ");
        String nombre = validarNombre();
        System.out.print("Apellido del paciente: ");
        String apellido = validarNombre();

        String contenido = dni + "," + nombre + "," + apellido;
        datosPacientes.add(contenido);

        System.out.println("Paciente agregado con éxito.");
    }

    // Opcion 3: programar citas
    private void programarCita() {
        if (!datosTerapeutas.isEmpty() & !datosPacientes.isEmpty()) {
            System.out.print("Ingrese la fecha y hora de la cita (ej. 2023-09-30 10:30): ");
            String fechaHora = validarFecha();
            System.out.print("Ingrese el DNI del terapeuta: ");
            String terapeuta = buscarPorDni("terapeuta");
            System.out.print("Ingrese el DNI del paciente: ");
            String paciente = buscarPorDni("paciente");

            String cita = paciente + "," + terapeuta + "," + fechaHora;
            datosCitas.add(cita);
            System.out.println("Cita programada con éxito.");
        } else {
            System.out.println("Primero debe registrar terapeutas y pacientes.");
        }
    }

    // Opcion 4 (A): ver reporte de citas
    private void verReporteCitas() {
        if (!datosCitas.isEmpty()) {
            System.out.println("\n===== En total se muestra " + datosCitas.size() + " cita(s) =====");
            for (int i = 0; i < datosCitas.size(); i++) {
                String[] partes = datosCitas.get(i).split(",");
                String dniPaciente = partes[0];
                String nombrePaciente = partes[1] + " " + partes[2];
                String dniTerapeuta = partes[3];
                String nombreTerapeuta = partes[4] + " " + partes[5];
                String fecha = partes[6];
                mostrarDetallesCita(dniPaciente, nombrePaciente, dniTerapeuta, nombreTerapeuta, fecha);
            }
        } else {
            System.out.println("No existen citas programadas");
        }
    }
    // Opcion 4 (B): formato de reporte de citas
    public void mostrarDetallesCita(String dniPac, String nombrePac, String dniTer, String nombreTer, String fecha) {
        System.out.println("\n===== Detalles de la Cita =====");
        System.out.println("Fecha y Hora: " + fecha);
        System.out.println("Paciente: " + nombrePac + " | DNI: " + dniPac);
        System.out.println("Terapeuta: " + nombreTer + " | DNI: " + dniTer);
    }

    // Opcion 5 y 6: ver lista de terapeutas o pacientes
    private void verListaPersonas(String persona) {
        ArrayList<String> arreglo = new ArrayList<>();
        if (persona.equals("Terapeuta"))
            arreglo = datosTerapeutas;
        else if (persona.equals("Paciente")) {
            arreglo = datosPacientes;
        }
        if (!arreglo.isEmpty()) {
            System.out.println("\n===== En total se muestra " + arreglo.size() + " " + persona + "(s) =====");
            for (int i = 0; i < arreglo.size(); i++) {
                String[] partes = arreglo.get(i).split(",");
                String dni = partes[0];
                String nombre = partes[1] + " " + partes[2];
                int id = i + 1;
                System.out.println(id + ") " + persona + ": " + nombre + " | DNI: " + dni);
            }
        } else {
            System.out.println("No hay datos de " + persona + "s registrados");
        }
    }

    ////////////////////////////// VALIDACIONES //////////////////////////////
    // Validacion de DNI
    private String validarDNI() {
        String dni;
        do {
            dni = scanner.nextLine();
            if (!dni.matches("\\d{8}")) {
                System.out.println("Error: El DNI debe contener 8 dígitos numericos. Inténtalo de nuevo.");
            }
        } while (!dni.matches("\\d{8}"));
        return dni;
    }

    // Validacion de Nombre
    private String validarNombre() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            input = scanner.nextLine();
            if (!input.matches("^[a-zA-Z ]+$")) {
                System.out.println("Error: Solo se permiten letras. Inténtalo de nuevo.");
            }
        } while (!input.matches("^[a-zA-Z ]+$"));
        return input;
    }

    // Validacion de Fecha
    private String validarFecha() {
        String fechaHora;
        do {
            fechaHora = scanner.nextLine();
            if (!fechaHora.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
                System.out.println("Error: Formato de fecha y hora inválido. Por favor, inténtalo de nuevo.");
            }
        } while (!fechaHora.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"));
        return fechaHora;
    }

    // Validacion de DNI existente en el registro para devolver los datos de la persona
    public String buscarPorDni(String persona) {
        String dni;
        String datosPersona = null;
        ArrayList<String> arreglo = new ArrayList<>();
        if (persona.equals("terapeuta"))
            arreglo = datosTerapeutas;
        else if (persona.equals("paciente")) {
            arreglo = datosPacientes;
        }

        do {
            dni = validarDNI();
            for (int i = 0; i < arreglo.size(); i++) {
                String[] partes = arreglo.get(i).split(",");
                if (partes[0].equals(dni)) {
                    datosPersona = arreglo.get(i);
                }
            }
            if (datosPersona == null) {
                System.out.println("Error: El DNI del " + persona + " no se encuentra regitrado. Inténtalo de nuevo.");
            }
        } while (datosPersona == null);
        return datosPersona;
    }
}