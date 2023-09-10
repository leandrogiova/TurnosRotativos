package com.neoris.turnosrotativos.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.turnosrotativos.entities.Jornada;

public class JornadaDTO {

    @NotNull(message = "El campo idEmpleado es obligatorio")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long idEmpleado;

    private Long nroDocumento;

    private String nombreCompleto;
    @NotNull(message = "El campo idConcepto es obligatorio")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer idConcepto;

    private String concepto;
    @NotNull(message = "El campo fecha es obligatorio")
    private LocalDate fecha;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer horasTrabajadas;

    public JornadaDTO() {

    }

    public JornadaDTO(String id, Long nroDocumento, String nombreCompleto, Integer idConcepto, LocalDate fecha,
            Integer horasTrabajadas) {

        this.nroDocumento = nroDocumento;
        this.nombreCompleto = nombreCompleto;

        this.fecha = fecha;
        this.horasTrabajadas = horasTrabajadas;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Long getnroDocumento() {
        return nroDocumento;
    }

    public void setnroDocumento(Long nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
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

        entity.setIdEmpleado(this.idEmpleado);
        entity.setNroDocumento(this.nroDocumento);
        entity.setIdConcepto(this.idConcepto);
        entity.setNombreConcepto(this.concepto);
        entity.setFecha(this.fecha);
        entity.setHorasTrabajadas(this.horasTrabajadas);

        return entity;
    }
}
