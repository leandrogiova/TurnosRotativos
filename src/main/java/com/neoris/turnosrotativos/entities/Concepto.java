package com.neoris.turnosrotativos.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.neoris.turnosrotativos.dto.ConceptoDTO;

@Entity(name = "conceptos")
public class Concepto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private Boolean laborable;
    private Integer hsMinimo;
    private Integer hsMaximo;

    public Concepto() {

    }

    public Concepto(Integer id, String nombre, Boolean laborable, Integer hsMinimo, Integer hsMaximo) {
        this.id = id;
        this.nombre = nombre;
        this.laborable = laborable;
        this.hsMinimo = hsMinimo;
        this.hsMaximo = hsMaximo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getLaborable() {
        return laborable;
    }

    public void setLaborable(Boolean laborable) {
        this.laborable = laborable;
    }

    public Integer getHsMinimo() {
        return hsMinimo;
    }

    public void setHsMinimo(Integer hsMinimo) {
        this.hsMinimo = hsMinimo;
    }

    public Integer getHsMaximo() {
        return hsMaximo;
    }

    public void setHsMaximo(Integer hsMaximo) {
        this.hsMaximo = hsMaximo;
    }

    public static ConceptoDTO toConceptoDTO(Concepto conceptoEntity) {
        ConceptoDTO conceptoDTO = new ConceptoDTO();
        conceptoDTO.setNombre(conceptoEntity.getNombre());
        conceptoDTO.setHsMaximo(conceptoEntity.getHsMaximo());
        conceptoDTO.setHsMinimo(conceptoEntity.getHsMinimo());
        conceptoDTO.setLaborable(conceptoEntity.getLaborable());

        return conceptoDTO;
    }

}
