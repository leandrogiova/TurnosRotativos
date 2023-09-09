package com.neoris.turnosrotativos.services.Implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.neoris.turnosrotativos.dto.JornadaDTO;

import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.repositorys.JornadaRepository;
import com.neoris.turnosrotativos.services.JornadaService;

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

}
