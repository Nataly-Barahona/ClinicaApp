package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Turno {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    public Turno(int id, Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        setEstado(EstadoTurno.PENDIENTE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("El médico no puede ser nulo.");
        }
        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas.");
        }
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado del turno no puede ser nulo.");
        }
        this.estado = estado;
    }

    // Control de conflictos de agenda: dos turnos son iguales si coinciden en médico y fechaHora
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turno turno = (Turno) o;
        return Objects.equals(medico, turno.medico) && 
               Objects.equals(fechaHora, turno.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medico, fechaHora);
    }

    // Formato: "[PENDIENTE] María García — Dr. Carlos Pérez (CARDIOLOGIA) — 2026-06-10T09:30"
    @Override
    public String toString() {
        return "[" + estado + "] " + 
               (paciente != null ? paciente.getNombre() : "") + " — " + 
               (medico != null ? medico.getNombre() + " (" + medico.getEspecialidad() + ")" : "") + " — " + 
               fechaHora;
    }
}

