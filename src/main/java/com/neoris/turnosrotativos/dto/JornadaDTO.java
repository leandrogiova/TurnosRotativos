package com.neoris.turnosrotativos.dto;

import java.time.LocalDate;

import com.neoris.turnosrotativos.entities.Jornada;

public class JornadaDTO {
    private Long id;

    private Long idEmpleado;

    private Integer idConcepto;

    private LocalDate fecha;

    private Integer horasTrabajadas;

    public JornadaDTO() {

    }

    public JornadaDTO(Long id, Long idEmpleado, Integer idConcepto, LocalDate fecha, Integer horasTrabajadas) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.idConcepto = idConcepto;
        this.fecha = fecha;
        this.horasTrabajadas = horasTrabajadas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    /*
    * 
    */
    public Jornada toEntity() {
        Jornada entity = new Jornada();

        entity.setId(this.id);
        entity.setIdEmpleado(this.idEmpleado);
        entity.setIdConcepto(this.idConcepto);
        entity.setFecha(this.fecha);
        entity.setHorasTrabajadas(this.horasTrabajadas);

        return entity;
    }
}
