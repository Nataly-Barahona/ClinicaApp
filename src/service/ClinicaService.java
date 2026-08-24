package service;

import interfaces.Consultable;
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

        if ( !nuevoMedico.esValido()) {
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

        nuevoMedico.setId(maximo + 1);
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

    public void registrarPaciente(Paciente paciente) {
        if (paciente == null || !paciente.esValido()) {
            System.out.println("Error: Los datos del paciente no son válidos.");
            return;
        }
        if (pacientes.contains(paciente)) {
            System.out.println("Error: Ya existe un paciente registrado con la cédula " + paciente.getCedula());
            return;
        }

        int maximo = 0;
        for (Paciente p : pacientes) {
            if (p.getId() > maximo) {
                maximo = p.getId();
            }
        }
        paciente.setId(maximo + 1);
        pacientes.add(paciente);
        System.out.println("Se registró el paciente");
        System.out.println(paciente.getDatosRegistro());
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
    public List<Turno> buscarPorMedico(Medico medico) {
        return List.of();
    }


    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {
        return List.of();
    }


    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        return List.of();
    }


}
