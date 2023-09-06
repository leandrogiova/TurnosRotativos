package com.neoris.turnosrotativos.dto;

import java.time.LocalDate;

import com.neoris.turnosrotativos.entities.Empleado;

public class EmpleadoDTO {
    private Long id;
    private Integer nroDocumento;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaIngreso;

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
