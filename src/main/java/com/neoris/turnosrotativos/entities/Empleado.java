package com.neoris.turnosrotativos.entities;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Required;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro_documento")

    private Integer nroDocumento;
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

    public Integer getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Integer nroDocumento) {
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

    public Empleado(Long id, Integer nroDocumento, String nombre, String apellido, String email,
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
     * 
     */
    public EmpleadoDTO toEmpleadoDTO() {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();

        empleadoDTO.setId(this.id);
        empleadoDTO.setNroDocumento(this.nroDocumento);
        empleadoDTO.setNombre(this.nombre);
        empleadoDTO.setApellido(this.apellido);
        empleadoDTO.setEmail(this.email);
        empleadoDTO.setFechaNacimiento(this.fechaNacimiento);
        empleadoDTO.setFechaIngreso(this.fechaIngreso);
        empleadoDTO.setFechaCreacion(this.fechaCreacion);

        return empleadoDTO;
    }

}
