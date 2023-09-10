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
     * Funcion obtenerTodosLasJornadas
     * Retorna una lista con todos las jornadas en la base de datos
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

    /*
     * Funcion obtenerJornadasPorDocumentoYFecha
     * Recibe como parametro un numero de documento de un empleado y una fecha
     * Setea el nombre completo del empleado y el nombre del concepto
     * Recibe como parametro un Long nroDocumento y un LocalDate fecha
     * Retorna una lista con todas las jornadas de ese empleado en esa fecha
     */
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

    /*
     * Funcion obtenerJornadasPorDocumento
     * Recibe como parametro un numero de documento de un empleado
     * Setea el nombre completo del empleado y el nombre del concepto
     * Recibe como parametro un Long nroDocumento
     * Retorna una lista con todas las jornadas de ese empleado
     */
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

    /*
     * Funcion obtenerJornadasPorFecha
     * Recibe como parametro una fecha LocalDate
     * Setea el nombre completo del empleado y el nombre del concepto
     * Recibe como parametro de una fecha LocalDate
     * Retorna una lista con todas las jornadas de ese empleado
     */
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
