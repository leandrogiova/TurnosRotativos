package com.neoris.turnosrotativos.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.services.Implementacion.EmpleadoServiceImpl;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoServiceImpl empleadoService;

    @RequestMapping(method = RequestMethod.POST)
    public void agregarEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        /*
         * Empleado empleado1 = new Empleado();
         * empleado1 = empleadoDTO.toEntity();
         * empleado1.setearfechaDeCreacion();
         * empleadoDTO = empleado1.toEmpleadoDTO();
         */
        empleadoService.agregarEmpleado(empleadoDTO.toEntity());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        // return ResponseEntity.ok(this.empleadoService.obtenerTodosLosEmpleados());
        return new ResponseEntity<>(this.empleadoService.obtenerTodosLosEmpleados(), HttpStatus.OK);
    }

}
