package com.neoris.turnosrotativos.services.Implementacion;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;
import com.neoris.turnosrotativos.repositorys.JornadaRepository;
import com.neoris.turnosrotativos.services.JornadaService;

@Service
public class JornadaServiceImpl implements JornadaService {

    @Autowired
    private JornadaRepository jornadaRepository;

    /*
     * Funcion obtenerTodosLosConceptos
     * Retorna una lista con todos los conceptos en la base de datos
     */
    public List<JornadaDTO> obtenerTodosLasJornadas() {
        return jornadaRepository.findAll()
                .stream()
                .map(Jornada::toEmpleadoDTO)
                .collect(Collectors.toList());

    }

    public List<JornadaDTO> obtenerJornadasPorDocumentoYFecha(Long nroDocumento, LocalDate fecha) {
        return jornadaRepository.findByNroDocumentoAndFecha(nroDocumento, fecha)
                .stream()
                .map(Jornada::toEmpleadoDTO)
                .collect(Collectors.toList());
    }

    public List<JornadaDTO> obtenerJornadasPorDocumento(Long nroDocumento) {
        return jornadaRepository.findByNroDocumento(nroDocumento)
                .stream()
                .map(Jornada::toEmpleadoDTO)
                .collect(Collectors.toList());
    }

    public List<JornadaDTO> obtenerJornadasPorFecha(LocalDate fecha) {
        return jornadaRepository.findByFecha(fecha)
                .stream()
                .map(Jornada::toEmpleadoDTO)
                .collect(Collectors.toList());
    }

}
