package com.neoris.turnosrotativos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.dto.ConceptoDTO;
import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.services.Implementacion.JornadaServiceImpl;

@RestController
@RequestMapping("/jornada")
public class JornadaController {

    @Autowired
    private JornadaServiceImpl jornadaService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<JornadaDTO>> obtenerTodosLosEmpleados() {

        return new ResponseEntity<>(this.jornadaService.obtenerTodosLasJornadas(), HttpStatus.OK);
    }

}
