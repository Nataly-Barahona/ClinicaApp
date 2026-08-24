package service;

import interfaces.Consultable;
import model.Medico;
import model.Paciente;
import model.Turno;

import java.awt.image.AreaAveragingScaleFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    //Medico
    public void registrarMedico(Medico medico) {

        if (!medico.esValido()) {
            throw new IllegalArgumentException("Los datos del medico no son validos.");

        }
        if (medicos.contains(medico)) {
            throw new IllegalArgumentException("Ya existe el medico con ese nombre y apellido.");
        }

        int maximo = 0;
        for (Medico medico1 : medicos) {
            if (medico1.getId() > maximo) {
                maximo = medico1.getId();
            }
        }
        int nuevoId = maximo + 1;
        medico.setId(maximo);
        medicos.add(medico);
        System.out.println("Se registro el medico ✅ ");
        System.out.println(medico.getDatosRegistro());
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


    //Paciente

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        return List.of();
    }
}
