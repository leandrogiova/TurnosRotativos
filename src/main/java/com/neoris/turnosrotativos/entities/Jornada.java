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

import com.neoris.turnosrotativos.dto.JornadaDTO;

@Entity
@Table(name = "jornadas")
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_empleado")

    private Long idEmpleado;

    @Column(name = "nro_documento")

    private Long nroDocumento;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "id_concepto")

    private Integer idConcepto;

    private String nombreConcepto;

    private LocalDate fecha;

    private Integer horasTrabajadas;

    public Jornada() {

    }

    public Jornada(Long id, Long nroDocumento, Integer idConcepto, String nombreCompleto, LocalDate fecha,
            Integer horasTrabajadas) {
        this.id = id;
        this.nroDocumento = nroDocumento;
        this.nombreCompleto = nombreCompleto;
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

    public Long getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(Long nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
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
    public JornadaDTO toJornadaDTO() {
        JornadaDTO jornadaDTO = new JornadaDTO();

        jornadaDTO.setnroDocumento(this.nroDocumento);
        jornadaDTO.setNombreCompleto(this.nombreCompleto);
        jornadaDTO.setConcepto(this.nombreConcepto);
        jornadaDTO.setFecha(this.fecha);
        jornadaDTO.setHorasTrabajadas(this.horasTrabajadas);

        return jornadaDTO;
    }

    /*
     * funcion JornadaDTO_Static
     * Transforma una clase Jornada a una clase jorandaDTO
     * Se utilizar para retornar todos las jornadas hacia afuera
     */
    public static JornadaDTO toJornadaDTO_Static(Jornada jornada) {
        JornadaDTO jornadaDTO = new JornadaDTO();

        jornadaDTO.setnroDocumento(jornada.getNroDocumento());
        jornadaDTO.setConcepto(jornada.getNombreConcepto());
        jornadaDTO.setNombreCompleto(jornada.getNombreCompleto());
        jornadaDTO.setFecha(jornada.getFecha());
        jornadaDTO.setHorasTrabajadas(jornada.getHorasTrabajadas());

        return jornadaDTO;
    }

}
