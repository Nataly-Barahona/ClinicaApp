package service;

import interfaces.Consultable;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ClinicaService implements Consultable {

    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Turno> turnos;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public ClinicaService() {

        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
        turnos = new ArrayList<>();
    }


    // =====================================================
    // GETTERS PARA DatosCSV
    // =====================================================

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }


    // =====================================================
    // OPCIÓN 1 - REGISTRAR PACIENTE
    // =====================================================

    public void registrarPaciente(Scanner scanner) {

        System.out.println("\n--- REGISTRAR PACIENTE ---");

        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        try {

            Paciente paciente = new Paciente(
                    cedula,
                    nombre,
                    apellido,
                    telefono
            );

            if (buscarPorCedula(cedula) != null) {

                System.out.println(
                        "Error: ya existe un paciente con esa cédula."
                );

                return;
            }

            paciente.setId(siguienteIdPaciente());

            pacientes.add(paciente);

            System.out.println(
                    "\nPaciente registrado correctamente."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }


    // =====================================================
    // OPCIÓN 2 - REGISTRAR MÉDICO
    // =====================================================

    public void registrarMedico(Scanner scanner) {

        System.out.println("\n--- REGISTRAR MÉDICO ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        Especialidad[] especialidades =
                Especialidad.values();

        System.out.println("\nEspecialidades:");

        for (int i = 0; i < especialidades.length; i++) {

            System.out.println(
                    (i + 1) + ". " + especialidades[i]
            );
        }

        try {

            System.out.print("Seleccione una especialidad: ");

            int opcion =
                    Integer.parseInt(scanner.nextLine());

            if (opcion < 1 ||
                    opcion > especialidades.length) {

                System.out.println("Especialidad inválida.");

                return;
            }

            Especialidad especialidad =
                    especialidades[opcion - 1];

            Medico medico = new Medico(
                    nombre,
                    apellido,
                    especialidad
            );

            if (buscarPorNombreApellido(
                    nombre,
                    apellido
            ) != null) {

                System.out.println(
                        "Error: ya existe un médico con ese nombre."
                );

                return;
            }

            medico.setId(siguienteIdMedico());

            medicos.add(medico);

            System.out.println(
                    "\nMédico registrado correctamente."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Debe ingresar un número válido."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }


    // =====================================================
    // OPCIÓN 3 - ASIGNAR TURNO
    // =====================================================

    public void asignarTurno(Scanner scanner) {

        System.out.println("\n--- ASIGNAR TURNO ---");

        System.out.print("Cédula del paciente: ");

        String cedula = scanner.nextLine();

        Paciente paciente =
                buscarPorCedula(cedula);

        if (paciente == null) {

            System.out.println("Paciente no encontrado.");

            return;
        }

        System.out.print("Nombre del médico: ");
        String nombre =
                scanner.nextLine();
        System.out.print("Apellido del médico: ");
        String apellido =   scanner.nextLine();

        Medico medico =    buscarPorNombreApellido(     nombre,      apellido      );

        if (medico == null) {

            System.out.println("Médico no encontrado.");

            return;
        }


        try {

            System.out.print("Año: ");
            int anio =
                    Integer.parseInt(scanner.nextLine());

            System.out.print("Mes: ");
            int mes =
                    Integer.parseInt(scanner.nextLine());

            System.out.print("Día: ");
            int dia =
                    Integer.parseInt(scanner.nextLine());

            System.out.print("Hora: ");
            int hora =
                    Integer.parseInt(scanner.nextLine());

            System.out.print("Minuto: ");
            int minuto =
                    Integer.parseInt(scanner.nextLine());


            LocalDateTime fechaHora =
                    LocalDateTime.of(
                            anio,
                            mes,
                            dia,
                            hora,
                            minuto
                    );


            Turno turno = new Turno(
                    siguienteIdTurno(),
                    paciente,
                    medico,
                    fechaHora,
                    EstadoTurno.PENDIENTE
            );


            if (hayConflicto(medico, fechaHora)) {

                System.out.println(
                        "El médico ya tiene un turno en ese horario."
                );

                return;
            }


            turnos.add(turno);

            System.out.println(
                    "\nTurno asignado correctamente."
            );

        } catch (Exception e) {

            System.out.println(
                    "Error: fecha u hora inválida."
            );
        }
    }


    // =====================================================
    // OPCIÓN 4 - LISTAR TURNOS DEL DÍA
    // =====================================================

    public void listarTurnosDelDia(Scanner scanner) {

        System.out.println("\n--- TURNOS DEL DÍA ---");

        try {

            System.out.print("Año: ");
            int anio =
                    Integer.parseInt(scanner.nextLine());

            System.out.print("Mes: ");
            int mes =
                    Integer.parseInt(scanner.nextLine());

            System.out.print("Día: ");
            int dia =
                    Integer.parseInt(scanner.nextLine());


            LocalDate fecha =
                    LocalDate.of(anio, mes, dia);


            List<Turno> resultado =
                    listarTurnosDelDia(fecha);


            if (resultado.isEmpty()) {

                System.out.println(
                        "No hay turnos para esta fecha."
                );

                return;
            }


            for (Turno turno : resultado) {

                System.out.println(turno);
            }

        } catch (Exception e) {

            System.out.println("Fecha inválida.");
        }
    }


    // =====================================================
    // OPCIÓN 5 - CANCELAR TURNO
    // =====================================================

    public void cancelarTurno(Scanner scanner) {

        System.out.println("\n--- CANCELAR TURNO ---");

        try {

            System.out.print("ID del turno: ");

            int id =
                    Integer.parseInt(scanner.nextLine());

            Turno turno =
                    buscarTurnoPorId(id);


            if (turno == null) {

                System.out.println("Turno no encontrado.");

                return;
            }


            if (turno.getEstado()
                    == EstadoTurno.CANCELADO) {

                System.out.println(
                        "El turno ya está cancelado."
                );

                return;
            }


            if (turno.getEstado()
                    == EstadoTurno.ATENDIDO) {

                System.out.println(
                        "No se puede cancelar un turno atendido."
                );

                return;
            }

            turno.setEstado(
                    EstadoTurno.CANCELADO
            );

            System.out.println(
                    "Turno cancelado correctamente."
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "El ID debe ser un número."
            );
        }
    }


    //OPCION 6

    public void verTurnosPorMedico(Scanner scanner) {

        System.out.println("\n--- TURNOS POR MÉDICO ---");

        System.out.print("Nombre: ");

        String nombre =
                scanner.nextLine();

        System.out.print("Apellido: ");

        String apellido =
                scanner.nextLine();


        Medico medico =
                buscarPorNombreApellido(
                        nombre,
                        apellido
                );


        if (medico == null) {

            System.out.println(
                    "Médico no encontrado."
            );

            return;
        }


        List<Turno> resultado =
                buscarPorMedico(medico);


        if (resultado.isEmpty()) {

            System.out.println(
                    "El médico no tiene turnos."
            );

            return;
        }


        for (Turno turno : resultado) {

            System.out.println(turno);
        }
    }


 //OPCION 7
    public void verTurnosPorPaciente(
            Scanner scanner) {

        System.out.println(
                "\n--- TURNOS POR PACIENTE ---"
        );

        System.out.print("Cédula: ");

        String cedula =      scanner.nextLine();

        Paciente paciente =      buscarPorCedula(cedula);

        if (paciente == null) {

            System.out.println(
                    "Paciente no encontrado."
            );
            return;
        }

        List<Turno> resultado =
                buscarPorPaciente(paciente);

        if (resultado.isEmpty()) {
            System.out.println(
                    "El paciente no tiene turnos."
            );
            return;
        }

        for (Turno turno : resultado) {
            System.out.println(turno);
        }
    }


    // =====================================================
    // OPCIÓN 8 - CAMBIAR ESTADO
    // =====================================================

    public void cambiarEstadoTurno(
            Scanner scanner) {

        System.out.println(
                "\n--- CAMBIAR ESTADO DE TURNO ---"
        );

        try {

            System.out.print("ID del turno: ");

            int id =
                    Integer.parseInt(
                            scanner.nextLine()
                    );


            Turno turno =
                    buscarTurnoPorId(id);


            if (turno == null) {

                System.out.println(
                        "Turno no encontrado."
                );

                return;
            }


            System.out.println("1. PENDIENTE");
            System.out.println("2. ATENDIDO");
            System.out.println("3. CANCELADO");

            System.out.print(
                    "Seleccione nuevo estado: "
            );


            int opcion =
                    Integer.parseInt(
                            scanner.nextLine()
                    );


            EstadoTurno[] estados =
                    EstadoTurno.values();


            if (opcion < 1 ||
                    opcion > estados.length) {

                System.out.println(
                        "Estado inválido."
                );

                return;
            }


            turno.setEstado(
                    estados[opcion - 1]
            );

            System.out.println(
                    "Estado actualizado correctamente."
            );

        } catch (Exception e) {

            System.out.println(
                    "Error al cambiar el estado."
            );
        }
    }


    // =====================================================
    // OPCIÓN 9 - LISTAR PACIENTES
    // =====================================================

    public void mostrarPacientes() {

        System.out.println(
                "\n--- LISTA DE PACIENTES ---"
        );

        List<Paciente> resultado =
                listarPacientes();


        if (resultado.isEmpty()) {

            System.out.println(
                    "No hay pacientes registrados."
            );

            return;
        }


        for (Paciente paciente : resultado) {

            System.out.println(paciente);
        }
    }


    // =====================================================
    // OPCIÓN 10 - LISTAR MÉDICOS
    // =====================================================

    public void mostrarMedicos() {

        System.out.println(
                "\n--- LISTA DE MÉDICOS ---"
        );

        List<Medico> resultado =
                listarMedicos();


        if (resultado.isEmpty()) {

            System.out.println(
                    "No hay médicos registrados."
            );

            return;
        }


        for (Medico medico : resultado) {

            System.out.println(medico);
        }
    }


    // =====================================================
    // MÉTODOS DE CONSULTABLE
    // =====================================================

    @Override
    public List<Turno> listarTurnosDelDia(
            LocalDate fecha) {

        List<Turno> resultado =
                new ArrayList<>();

        for (Turno turno : turnos) {

            if (turno.getFechaHora()
                    .toLocalDate()
                    .equals(fecha)) {

                resultado.add(turno);
            }
        }

        resultado.sort(
                Comparator.comparing(
                        Turno::getFechaHora
                )
        );

        return resultado;
    }


    @Override
    public List<Turno> buscarPorMedico(
            Medico medico) {

        List<Turno> resultado =
                new ArrayList<>();

        for (Turno turno : turnos) {

            if (turno.getMedico()
                    .equals(medico)) {

                resultado.add(turno);
            }
        }

        resultado.sort(
                Comparator.comparing(
                        Turno::getFechaHora
                )
        );

        return resultado;
    }


    @Override
    public List<Turno> buscarPorPaciente(
            Paciente paciente) {

        List<Turno> resultado =
                new ArrayList<>();

        for (Turno turno : turnos) {

            if (turno.getPaciente()
                    .equals(paciente)) {

                resultado.add(turno);
            }
        }

        resultado.sort(
                Comparator.comparing(
                        Turno::getFechaHora
                )
        );

        return resultado;
    }


    // =====================================================
    // LISTAR PACIENTES
    // =====================================================

    public List<Paciente> listarPacientes() {

        List<Paciente> resultado =
                new ArrayList<>(pacientes);

        resultado.sort(
                Comparator
                        .comparing(
                                Paciente::getApellido,
                                String.CASE_INSENSITIVE_ORDER
                        )
                        .thenComparing(
                                Paciente::getNombre,
                                String.CASE_INSENSITIVE_ORDER
                        )
        );

        return resultado;
    }


    // =====================================================
    // LISTAR MÉDICOS
    // =====================================================

    public List<Medico> listarMedicos() {

        List<Medico> resultado =
                new ArrayList<>(medicos);

        resultado.sort(
                Comparator
                        .comparing(
                                Medico::getApellido,
                                String.CASE_INSENSITIVE_ORDER
                        )
                        .thenComparing(
                                Medico::getNombre,
                                String.CASE_INSENSITIVE_ORDER
                        )
        );

        return resultado;
    }


    // =====================================================
    // BUSCAR PACIENTE
    // =====================================================

    public Paciente buscarPorCedula(
            String cedula) {

        for (Paciente paciente : pacientes) {

            if (paciente.getCedula()
                    .equalsIgnoreCase(
                            cedula.trim()
                    )) {

                return paciente;
            }
        }

        return null;
    }


    // =====================================================
    // BUSCAR MÉDICO
    // =====================================================

    public Medico buscarPorNombreApellido(
            String nombre,
            String apellido) {

        for (Medico medico : medicos) {

            if (medico.getNombre()
                    .equalsIgnoreCase(nombre.trim())
                    &&
                    medico.getApellido()
                            .equalsIgnoreCase(
                                    apellido.trim()
                            )) {

                return medico;
            }
        }

        return null;
    }


    // =====================================================
    // BUSCAR TURNO
    // =====================================================

    public Turno buscarTurnoPorId(int id) {

        for (Turno turno : turnos) {

            if (turno.getId() == id) {

                return turno;
            }
        }

        return null;
    }


    // =====================================================
    // VALIDAR CONFLICTO
    // =====================================================

    private boolean hayConflicto(
            Medico medico,
            LocalDateTime fechaHora) {

        for (Turno turno : turnos) {

            if (turno.getMedico()
                    .equals(medico)
                    &&
                    turno.getFechaHora()
                            .equals(fechaHora)
                    &&
                    turno.getEstado()
                            != EstadoTurno.CANCELADO) {

                return true;
            }
        }

        return false;
    }


    // =====================================================
    // GENERAR IDS
    // =====================================================

    private int siguienteIdPaciente() {

        int mayor = 0;

        for (Paciente paciente : pacientes) {

            if (paciente.getId() > mayor) {

                mayor = paciente.getId();
            }
        }

        return mayor + 1;
    }


    private int siguienteIdMedico() {

        int mayor = 0;

        for (Medico medico : medicos) {

            if (medico.getId() > mayor) {

                mayor = medico.getId();
            }
        }

        return mayor + 1;
    }


    private int siguienteIdTurno() {

        int mayor = 0;

        for (Turno turno : turnos) {

            if (turno.getId() > mayor) {

                mayor = turno.getId();
            }
        }

        return mayor + 1;
    }
}