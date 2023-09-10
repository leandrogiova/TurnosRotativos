package com.neoris.turnosrotativos.entities;

import java.time.LocalDate;

import javax.persistence.*;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro_documento")
    private Long nroDocumento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email")
    private String email;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Long nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Empleado(Long id, Long nroDocumento, String nombre, String apellido, String email,
            LocalDate fechaNacimiento,
            LocalDate fechaIngreso, LocalDate fechaCreacion) {
        this.id = id;
        this.nroDocumento = nroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = fechaIngreso;
        this.fechaCreacion = fechaCreacion;
    }

    public Empleado() {
    }

    /*
     * Setea la fecha de creacion del empleado
     */
    public void setearfechaDeCreacion() {
        this.fechaCreacion = LocalDate.now();
        System.out.println("\n\n\n\nViendo la fecha:" + this.fechaCreacion);
    }

    /*
     * funcion EmpleadoDTO_Static
     * Transforma una clase Empleado a una clase empleadoDTO
     * Se utilizar para retornar todos los empleados hacia afuera
     */
    public static EmpleadoDTO toEmpleadoDTO_Static(Empleado empleado) {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();

        empleadoDTO.setNroDocumento(empleado.getNroDocumento());
        empleadoDTO.setNombre(empleado.getNombre());
        empleadoDTO.setApellido(empleado.getApellido());
        empleadoDTO.setEmail(empleado.getEmail());
        empleadoDTO.setFechaNacimiento(empleado.getFechaNacimiento());
        empleadoDTO.setFechaIngreso(empleado.getFechaIngreso());
        empleadoDTO.setFechaCreacion(empleado.getFechaCreacion());

        return empleadoDTO;
    }

    /*
     * funcion EmpleadoDTO
     * Transforma una clase Empleado a una clase empleadoDTO
     * Para su cominucacion al exterior
     */
    public EmpleadoDTO toEmpleadoDTO() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();

        empleadoDTO.setNroDocumento(this.nroDocumento);
        empleadoDTO.setNombre(this.nombre);
        empleadoDTO.setApellido(this.apellido);
        empleadoDTO.setEmail(this.email);
        empleadoDTO.setFechaNacimiento(this.fechaNacimiento);
        empleadoDTO.setFechaIngreso(this.fechaIngreso);
        empleadoDTO.setFechaCreacion(this.fechaCreacion);

        return empleadoDTO;
    }

    /*
     * funcion setearNombreApellido
     * Trasnsdorma un String con la primer letra en mayusculas
     * y el resto de las letras en minusculas
     * No verifica si el string contiene numeros o letras ya que se va a utilizar
     * luego de verificar si el string contiene solamente letras y no caracteres
     * especiales
     * Recibe como parametro un String
     * Retorna un String con la primer letra mayuscula y el resto en minusculas
     * Ejemplo: setearNombreApellido("LeAnDro") ==> "Leandro"
     */
    public String setearNombreApellido(String stringNombreApellido) {
        char primerCaracter = Character.toUpperCase(stringNombreApellido.charAt(0));

        String restoCadena = stringNombreApellido.substring(1).toLowerCase();

        stringNombreApellido = primerCaracter + restoCadena;
        return stringNombreApellido;
    }

}
