package com.neoris.turnosrotativos.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.turnosrotativos.entities.Jornada;

public class JornadaDTO {
    private String id;

    private Long nroDocumento;

    private String nombreCompleto;

    private Integer idConcepto;

    private LocalDate fecha;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer horasTrabajadas;

    public JornadaDTO() {

    }

    public JornadaDTO(String id, Long nroDocumento, String nombreCompleto, Integer idConcepto, LocalDate fecha,
            Integer horasTrabajadas) {
        this.id = id;
        this.nroDocumento = nroDocumento;
        this.nombreCompleto = nombreCompleto;
        this.idConcepto = idConcepto;
        this.fecha = fecha;
        this.horasTrabajadas = horasTrabajadas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        entity.setnroDocumento(this.nroDocumento);
        entity.setIdConcepto(this.idConcepto);
        entity.setFecha(this.fecha);
        entity.setHorasTrabajadas(this.horasTrabajadas);

        return entity;
    }
}
