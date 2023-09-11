package com.neoris.turnosrotativos.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.neoris.turnosrotativos.entities.Empleado;

public class EmpleadoDTO {

    private Long id;

    @NotNull(message = "El campo nroDocumento es obligatorio")
    private Long nroDocumento;
    @NotNull(message = "El campo nombre es obligatorio")
    @NotBlank(message = "El campo nombre no puede estar vacio")
    private String nombre;
    @NotNull(message = "El campo apellido es obligatorio")
    @NotBlank(message = "El campo apellido no puede estar vacio")
    private String apellido;
    @NotNull(message = "El campo email es obligatorio")
    @Email(message = "El email ingresado no es correcto.")
    private String email;
    @NotNull(message = "El campo fechaNacimiento es obligatorio")
    private LocalDate fechaNacimiento;
    @NotNull(message = "El campo fechaIngreso es obligatorio")
    private LocalDate fechaIngreso;
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

    /*
     * 
     */
    public Empleado toEntity() {
        Empleado entity = new Empleado();

        entity.setId(this.id);
        entity.setNroDocumento(this.nroDocumento);
        entity.setNombre(this.nombre);
        entity.setApellido(this.apellido);
        entity.setEmail(this.email);
        entity.setFechaNacimiento(fechaNacimiento);
        entity.setFechaIngreso(fechaIngreso);

        return entity;
    }

}
