package com.neoris.turnosrotativos.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.dto.JornadaDTO;

@Entity
@Table(name = "jornadas")
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "")
    @NotBlank(message = "")
    private Long idEmpleado;
    @NotNull(message = "")
    @NotBlank(message = "")
    private Integer idConcepto;
    @NotNull(message = "")
    @NotBlank(message = "")
    private LocalDate fecha;

    private Integer horasTrabajadas;

    public Jornada() {

    }

    public Jornada(Long id, Long idEmpleado, Integer idConcepto, LocalDate fecha, Integer horasTrabajadas) {
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
     * funcion JornadaDTO
     * Transforma una clase Jornada a una clase JornadaDTO
     * Para su cominucacion al exterior
     */
    public JornadaDTO toEmpleadoDTO() {
        JornadaDTO jornadaDTO = new JornadaDTO();

        jornadaDTO.setId(this.id);
        jornadaDTO.setIdEmpleado(this.idEmpleado);
        jornadaDTO.setIdConcepto(this.idConcepto);
        jornadaDTO.setFecha(this.fecha);
        jornadaDTO.setHorasTrabajadas(this.horasTrabajadas);

        return jornadaDTO;
    }

    /*
     * funcion JornadaDTO_Static
     * Transforma una clase Jornada a una clase jorandaDTO
     * Se utilizar para retornar todos las jornadas hacia afuera
     */
    public static JornadaDTO toEmpleadoDTO_Static(Jornada jornada) {
        JornadaDTO jornadaDTO = new JornadaDTO();

        jornadaDTO.setId(jornada.getId());
        jornadaDTO.setIdEmpleado(jornada.getIdEmpleado());
        jornadaDTO.setIdConcepto(jornada.getIdConcepto());
        jornadaDTO.setFecha(jornada.getFecha());
        jornadaDTO.setHorasTrabajadas(jornada.getHorasTrabajadas());

        return jornadaDTO;
    }

}
