package com.neoris.turnosrotativos.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.services.Implementacion.JornadaServiceImpl;

@RestController
@RequestMapping("/jornada")
public class JornadaController {

    @Autowired
    private JornadaServiceImpl jornadaService;

    /*
     * Funcion obtenerTodosLosEmpleados
     * Va a ir a buscar todas jornadas a la base de datos
     * Puede o no recibir dos parametros, un numero de documeto, y una fecha para
     * filtrar los resultados de la busqueda
     * Dependiendo que parametros se le pasen va a llamar a uno u otro metodo del
     * Service
     * Recibe como paramtros un Long nroDocumento y un LocalDate fecha
     * Retorna un ResponseEntity<List<JornadaDTO>> con un Status ok 200.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<JornadaDTO>> obtenerTodosLosEmpleados(
            @RequestParam(required = false) Long nroDocumento,
            @RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        if (nroDocumento != null && fecha != null) {
            return new ResponseEntity<>(jornadaService.obtenerJornadasPorDocumentoYFecha(nroDocumento, fecha),
                    HttpStatus.OK);
        } else if (nroDocumento != null) {

            return new ResponseEntity<>(jornadaService.obtenerJornadasPorDocumento(nroDocumento), HttpStatus.OK);
        } else if (fecha != null) {

            return new ResponseEntity<>(jornadaService.obtenerJornadasPorFecha(fecha), HttpStatus.OK);
        } else {

            return new ResponseEntity<>(jornadaService.obtenerTodosLasJornadas(), HttpStatus.OK);
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<JornadaDTO> agregarJornada(@Valid @RequestBody JornadaDTO jornadaDTO) {

        JornadaDTO jornadaDTO_ = jornadaService.agregarJornada(jornadaDTO.toEntity());
        jornadaDTO_.setIdConcepto(null);
        jornadaDTO_.setIdEmpleado(null);
        return new ResponseEntity<JornadaDTO>(jornadaDTO_,
                HttpStatus.CREATED);
    }

}
