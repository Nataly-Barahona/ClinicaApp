package model;

import interfaces.Registrable;

public class Medico implements Registrable {
    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;


    public Medico(int id, String nombre, String apellido, Especialidad especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }


    public Medico(String nombre, String apellido, Especialidad especialidad) {
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {


        if (nombre == null || nombre.trim().isBlank()) {

            throw new IllegalArgumentException("Nombre no puede estar vacio ❌, ingrese un nombre valido");


        }
        String nombreLimpio = nombre.trim();

        String SOLO_LETRAS_EXPRESION_REGULAR = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";

        if (!nombreLimpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("Nombre solo puede contener letras ❌");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {

        if (apellido == null || apellido.trim().isBlank()) {

            throw new IllegalArgumentException("Apellido no puede estar vacio ❌, ingrese un apellido valido");


        }
        String apellidoLimpio = nombre.trim();

        String SOLO_LETRAS_EXPRESION_REGULAR = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";

        if (!apellidoLimpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("Apellido solo puede contener letras ❌");
        }
        this.apellido = apellido;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {

        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad es obligatoria y debe ser una de las opciones válidas (GENERAL, PEDIATRIA, CARDIOLOGIA, URGENCIAS)");
        }
        this.especialidad = especialidad;
    }


    @Override
    public String toString() {
        return "Dr. " + nombre + " " + apellido + " - " + especialidad;

    }


}
