package service;
import java.util.Scanner;
import interfaces.Consultable;
import model.EstadoTurno;
import model.Especialidad;
import model.Medico;
import model.Paciente;
import model.Turno;

import java.awt.image.AreaAveragingScaleFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ClinicaService implements Consultable {

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Turno> turnos = new ArrayList<>();


    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    // =======================================================
    // METODOS DE MEDICOS  ===================================
    // =======================================================

    public  void registrarMedico(Scanner scanner) {
        System.out.println("\n--- REGISTRAR MÉDICO ---");

        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();

        System.out.println("Especialidades: GENERAL, PEDIATRIA, CARDIOLOGIA, URGENCIAS");
        System.out.print("Ingrese la especialidad: ");
        String especialidadTexto = scanner.nextLine().trim().toUpperCase();

        Especialidad especialidad = Especialidad.valueOf(especialidadTexto);

        Medico nuevoMedico = new Medico(nombre, apellido, especialidad);

        if (!nuevoMedico.esValido()) {
            System.out.println("Error: Los datos del médico no son válidos. ❌");
            return;
        }

        if (medicos.contains(nuevoMedico)) {
            System.out.println("Error: Ya existe un médico registrado con ese nombre y apellido. ❌");
            return;
        }

        int maximo = 0;
        for (Medico m : medicos) {
            if (m.getId() > maximo) {
                maximo = m.getId();
            }
        }
        int nuevoId = maximo+1;
        nuevoMedico.setId(nuevoId);
        medicos.add(nuevoMedico);
        System.out.println("Se registró el médico ✅");
        System.out.println(nuevoMedico.getDatosRegistro());



    }

    public Medico buscarPorNombreApellido(String nombre, String apellido) {

        for (Medico medico : medicos) {
            if (medico.getNombre().equalsIgnoreCase(nombre) && medico.getApellido().equalsIgnoreCase(apellido)) {

                System.out.println("Medico encontrado ✅");
                System.out.println(medico.getDatosRegistro());
                return medico;
            }
        }
        System.out.println("No se encontro el medico con nombre : " + nombre + " y apellido " + apellido + " ❌");
        return null;
    }

    public void listarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados ❌");
            return;
        }
        List<Medico> copiaMedicos = new ArrayList<>(medicos);
        copiaMedicos.sort(Comparator.comparing(Medico::getEspecialidad).thenComparing(Medico::getApellido));
        for (Medico medico : copiaMedicos) {
            System.out.println(medico.toString());
        }

    }
    // =======================================================
    // MÉTODOS DE PACIENTE ===================================
    // =======================================================

    public void registrarPaciente(Scanner scanner) {
        System.out.println("\n--- REGISTRAR PACIENTE ---");

        System.out.print("Ingrese la cédula: ");
        String cedula = scanner.nextLine().trim();

        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine().trim();

        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine().trim();

        Paciente nuevoPaciente = new Paciente(cedula, nombre, apellido, telefono);

        if (!nuevoPaciente.esValido()) {
            System.out.println("Error: Los datos del paciente no son válidos");
            return;
        }

        if (pacientes.contains(nuevoPaciente)) {
            System.out.println("Error: Ya existe un paciente registrado con esa cédula");
            return;
        }

        int maximo = 0;
        for (Paciente p : pacientes) {
            if (p.getId() > maximo) {
                maximo = p.getId();
            }
        }

        nuevoPaciente.setId(maximo + 1);
        pacientes.add(nuevoPaciente);

        System.out.println("Se registró el paciente con éxito");
        System.out.println(nuevoPaciente.getDatosRegistro());
    }

    public Paciente buscarPorCedula(String cedula) {
        if (cedula == null) return null;
        for (Paciente paciente : pacientes) {
            if (paciente.getCedula().equals(cedula.trim())) {
                return paciente;
            }
        }
        return null;
    }

    public void listarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }
        List<Paciente> copia = new ArrayList<>(pacientes);
        copia.sort(java.util.Comparator.comparing(Paciente::getApellido)
                .thenComparing(Paciente::getNombre));
        for (Paciente p : copia) {
            System.out.println(p.toString());
        }
    }


    @Override
    public List<Turno> buscarPorMedico( Medico medico) {
        List<Turno> resultado =  new ArrayList<>();
        for (Turno turno : turnos) {
            if (turno.getMedico().equals(medico)) {
                resultado.add(turno);
            }
        }
        return resultado;
    }


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
        resultado.sort(Comparator.comparing(Turno::getFechaHora) );
        return resultado;
    }


    @Override
    public List<Turno> buscarPorPaciente(
            Paciente paciente) {

        List<Turno> resultado =
                new ArrayList<>();
        for (Turno turno : turnos) {
            if (turno.getPaciente().equals(paciente)) {
                resultado.add(turno);
            }
        }
        return resultado;
    }

//Turno

    public void asignarTurno(Turno turno) {

        Paciente pacienteExistente = buscarPorCedula(turno.getPaciente().getCedula());
        if (pacienteExistente == null) {
            throw new IllegalArgumentException("El paciente no esta registrado.");
        }

        Medico medicoExistente = buscarPorNombreApellido(turno.getMedico().getNombre(), turno.getMedico().getApellido());
        if (medicoExistente == null) {
            throw new IllegalArgumentException("El medico no esta registrado.");
        }

        if (turnos.contains(turno)) {
            throw new IllegalArgumentException("El medico ya tiene un turno asignado en esa fecha y hora.");
        }

        int maximo = 0;
        for (Turno t : turnos) {
            if (t.getId() > maximo) {
                maximo = t.getId();
            }
        }
        int nuevoId = maximo + 1;
        turno.setId(nuevoId);
        turnos.add(turno);
        System.out.println("Se asigno el turno ✅ ");
        System.out.println(turno);
    }

    public void cancelarTurno(int idTurno) {
        Turno turno = buscarTurnoPorId(idTurno);

        if (turno == null) {
            System.out.println("Turno no encontrado ❌");
            return;
        }
        if (turno.getEstado() == EstadoTurno.ATENDIDO || turno.getEstado() == EstadoTurno.CANCELADO) {
            System.out.println("No se puede cancelar un turno en estado " + turno.getEstado() + ".");
            return;
        }
        turno.setEstado(EstadoTurno.CANCELADO);
        System.out.println("Turno cancelado ✅");
        System.out.println(turno);
    }

    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevo) {
        Turno turno = buscarTurnoPorId(idTurno);

        if (turno == null) {
            System.out.println("Turno no encontrado ❌");
            return;
        }
        turno.setEstado(nuevo);
        System.out.println("Estado del turno actualizado a " + nuevo + " ✅");
        System.out.println(turno);
    }

    private Turno buscarTurnoPorId(int idTurno) {
        for (Turno t : turnos) {
            if (t.getId() == idTurno) {
                return t;
            }
        }
        return null;
    }
}





