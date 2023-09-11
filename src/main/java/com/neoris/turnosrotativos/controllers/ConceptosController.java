package com.neoris.turnosrotativos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.dto.ConceptoDTO;

import com.neoris.turnosrotativos.services.Implementacion.ConceptoServiceImpl;

@RestController
@RequestMapping("/concepto")
public class ConceptosController {

    @Autowired
    private ConceptoServiceImpl conceptoService;

    /*
     * Funcion obtenerTodosLosEmpleados
     * Retorna todos los conceptos de la base de datos
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ConceptoDTO>> obtenerTodosLosEmpleados() {

        return new ResponseEntity<>(this.conceptoService.obtenerTodosLosConceptos(), HttpStatus.OK);
    }

}
