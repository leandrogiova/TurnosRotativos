package com.neoris.turnosrotativos.services.Implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.dto.ConceptoDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.repositorys.ConceptoRepository;
import com.neoris.turnosrotativos.services.ConceptoService;

@Service
public class ConceptoServiceImpl implements ConceptoService {

    @Autowired
    private ConceptoRepository conceptoRepository;

    /*
     * Funcion obtenerTodosLosConceptos
     * Retorna una lista con todos los conceptos en la base de datos
     */
    public List<ConceptoDTO> obtenerTodosLosConceptos() {
        return conceptoRepository.findAll()
                .stream()
                .map(Concepto::toConceptoDTO)
                .collect(Collectors.toList());
    }

}
