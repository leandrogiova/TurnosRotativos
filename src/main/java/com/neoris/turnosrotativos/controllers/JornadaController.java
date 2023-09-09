package com.neoris.turnosrotativos.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.services.Implementacion.JornadaServiceImpl;

@RestController
@RequestMapping("/jornada")
public class JornadaController {

    @Autowired
    private JornadaServiceImpl jornadaService;

    // @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<JornadaDTO>> obtenerTodosLosEmpleados10() {
        return new ResponseEntity<>(this.jornadaService.obtenerTodosLasJornadas(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<JornadaDTO>> obtenerTodosLosEmpleados(
            @RequestParam(required = false) Long nroDocumento,
            @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        if (nroDocumento != null && fecha != null) {
            // Filtrar por número de documento y fecha
            return new ResponseEntity<>(jornadaService.obtenerJornadasPorDocumentoYFecha(nroDocumento, fecha),
                    HttpStatus.OK);
        } else if (nroDocumento != null) {
            // Filtrar por número de documento
            return new ResponseEntity<>(jornadaService.obtenerJornadasPorDocumento(nroDocumento), HttpStatus.OK);
        } else if (fecha != null) {
            // Filtrar por fecha
            return new ResponseEntity<>(jornadaService.obtenerJornadasPorFecha(fecha), HttpStatus.OK);
        } else {
            // Obtener todas las jornadas si no se proporcionan parámetros
            return new ResponseEntity<>(jornadaService.obtenerTodosLasJornadas(), HttpStatus.OK);
        }

    }

}
