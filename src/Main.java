import datos.DatosCSV;
import service.ClinicaService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ClinicaService servicio = new ClinicaService();
        DatosCSV.cargar(servicio);
        Scanner scanner = new Scanner(System.in);

        int opcion = -1;

        do {
            mostrarMenu();
            System.out.print("Bienvenido. ");
            System.out.print("Digite una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                if (opcion < 0 || opcion > 10) {
                    System.out.println("Opción no válida. Ingrese un número del 0 al 10.");
                    continue;
                }

                switch (opcion) {
                    case 1:
                        System.out.println("REGISTRAR PACIENTE");
                        servicio.registrarPaciente(scanner);
                        break;

                    case 2:
                        System.out.println("REGISTRAR MEDICO");
                        servicio.registrarMedico(scanner);
                        break;

                    case 3:
                        System.out.println("ASIGNAR TURNO");
                        servicio.asignarTurno(scanner);
                        break;

                    case 4:
                        System.out.println("LISTAR TURNOS DEL DÍA");
                        servicio.listarTurnosDelDia(scanner);
                        break;

                    case 5:
                        System.out.println("CANCELAR TURNO");
                        servicio.cancelarTurno(scanner);
                        break;

                    case 6:
                        System.out.println("VER TURNOS POR MÉDICO");
                        servicio.verTurnosPorMedico(scanner);
                        break;

                    case 7:
                        System.out.println("VER TURNOS POR PACIENTE");
                        servicio.verTurnosPorPaciente(scanner);
                        break;

                    case 8:
                        System.out.println("CAMBIAR ESTADO DE TURNO");
                        servicio.cambiarEstadoTurno(scanner);
                        break;

                    case 9:
                        System.out.println("LISTAR PACIENTES");
                        servicio.mostrarPacientes();
                        break;

                    case 10:
                        System.out.println("FUNCION LISTAR MEDICOS");
                        servicio.mostrarMedicos();
                        break;

                    case 0:
                        System.out.println("ELECCIÓN SALIR...");
                        DatosCSV.guardar(servicio);
                        System.out.println("\n Proceso finalizado. Datos guardados.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida. Debe ingresar un número del 0 al 10.");
            }

        } while (opcion != 0);
        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     CLINICAAPP EQUIPO 2 - MENÚ       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Registrar paciente               ║");
        System.out.println("║  2. Registrar médico                 ║");
        System.out.println("║  3. Asignar turno                    ║");
        System.out.println("║  4. Listar turnos del día            ║");
        System.out.println("║  5. Cancelar turno                   ║");
        System.out.println("║  6. Ver turnos por médico            ║");
        System.out.println("║  7. Ver turnos por paciente          ║");
        System.out.println("║  8. Cambiar estado de turno          ║");
        System.out.println("║  9. Listar pacientes                 ║");
        System.out.println("║ 10. Listar médicos                   ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  0. Salir                            ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
    }






}