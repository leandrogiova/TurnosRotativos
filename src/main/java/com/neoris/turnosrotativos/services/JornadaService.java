package com.neoris.turnosrotativos.services;

import java.time.LocalDate;
import java.util.List;

import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;

public interface JornadaService {

    public JornadaDTO agregarJornada(Jornada jornada);

    public List<JornadaDTO> obtenerTodosLasJornadas();

    public List<JornadaDTO> obtenerJornadasPorDocumentoYFecha(Long nroDocumento, LocalDate fecha);

    public List<JornadaDTO> obtenerJornadasPorDocumento(Long nroDocumento);

    public List<JornadaDTO> obtenerJornadasPorFecha(LocalDate fecha);

    // Metodos para validar y demás funcionalidades

    public void validarEmpleadoConElMismoConcepto(Jornada jornada);

    public LocalDate[] calcularInicioYFinSemana(LocalDate fecha);

    public void validarCantidadDeHorasSemanales(Jornada jornada, LocalDate[] fechas);

    public void validarCantidadDeTurnosSemanales(Jornada jornada, LocalDate[] fechas);

    public void validarCantidadDeHorasPorDiaDeUnEmpleado(Jornada jornada);

    public void validarConceptoFecha(Jornada jornada);

    public void validarDiaLibre(Jornada jornada);

    public Empleado validarIdEmpleado(Long idEmpleado);

    public Concepto validarConcepto(Integer idConcepto, Integer horasTrabajadas);

    public void validarHoras(Concepto concepto, Integer horasTrabajadas);

    public String setearNombreDelConcepto(Integer idConcepto);

    public String setearNombreCompletoDelEmpleado(Long numeroDocumento);

}
