package com.neoris.turnosrotativos.services.Implementacion;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.repositorys.ConceptoRepository;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;
import com.neoris.turnosrotativos.repositorys.JornadaRepository;
import com.neoris.turnosrotativos.services.JornadaService;

@Service
public class JornadaServiceImpl implements JornadaService {

    @Autowired
    private JornadaRepository jornadaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ConceptoRepository conceptoRepository;

    /*
     * Funcion obtenerTodosLosConceptos
     * Retorna una lista con todos los conceptos en la base de datos
     */
    public List<JornadaDTO> obtenerTodosLasJornadas() {
        return jornadaRepository.findAll()
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toEmpleadoDTO();
                })
                .collect(Collectors.toList());

    }

    public List<JornadaDTO> obtenerJornadasPorDocumentoYFecha(Long nroDocumento, LocalDate fecha) {
        return jornadaRepository.findByNroDocumentoAndFecha(nroDocumento, fecha)
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toEmpleadoDTO();
                })
                .collect(Collectors.toList());
    }

    public List<JornadaDTO> obtenerJornadasPorDocumento(Long nroDocumento) {
        return jornadaRepository.findByNroDocumento(nroDocumento)
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toEmpleadoDTO();
                })
                .collect(Collectors.toList());
    }

    public List<JornadaDTO> obtenerJornadasPorFecha(LocalDate fecha) {
        return jornadaRepository.findByFecha(fecha)
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toEmpleadoDTO();
                })
                .collect(Collectors.toList());
    }

    /*
     * Funcion setearNombreDelConcepto
     * Recibe un id de un Concepto
     * Retorna el nombre de ese Concepto que recibe como parametro
     */
    public String setearNombreDelConcepto(Integer idConcepto) {

        Optional<Concepto> concepto = conceptoRepository.findById(idConcepto);

        if (concepto.isPresent()) {
            return concepto.get().getNombre();
        } else {
            return null;
        }

    }

    /*
     * Funcion setearNombreDelConcepto
     * Recibe un id de un Concepto
     * Retorna el nombre de ese Concepto que recibe como parametro
     */
    public String setearNombreCompletoDelEmpleado(Long numeroDocumento) {

        Optional<Empleado> empleado = empleadoRepository.findByNroDocumento(numeroDocumento);
        if (empleado.isPresent()) {
            return empleado.get().getNombre() + " " + empleado.get().getApellido();
        } else {
            return null;
        }

    }

}
