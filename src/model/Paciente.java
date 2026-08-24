package model;
import interfaces.Registrable;
import java.util.Objects;
public class Paciente {
    private int id;
    private String telefono;
    private String cedula;
    private String nombre;
    private String apellido;

    public Paciente(String telefono, String cedula, String nombre, String apellido ) {
        setTelefono(telefono);
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
    }
    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        this(cedula, nombre, apellido, telefono);
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula no puede estar vacía.");
        }
        this.cedula = cedula.trim();
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido.trim();
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^[0-9]{7,10}$")) {
            throw new IllegalArgumentException("El teléfono debe contener solo dígitos y tener entre 7 y 10 caracteres.");
        }
        this.telefono = telefono.trim();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cedula, paciente.cedula);
    }
    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + cedula + " - " + telefono;
    }
}
