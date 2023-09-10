package com.neoris.turnosrotativos.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.turnosrotativos.entities.Jornada;

public class JornadaDTO {

    private Long nroDocumento;

    private String nombreCompleto;

    private String concepto;

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

        entity.setNroDocumento(this.nroDocumento);
        entity.setNombreConcepto(this.concepto);
        entity.setFecha(this.fecha);
        entity.setHorasTrabajadas(this.horasTrabajadas);

        return entity;
    }
}
