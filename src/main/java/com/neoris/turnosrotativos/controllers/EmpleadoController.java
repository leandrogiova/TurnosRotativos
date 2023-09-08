package com.neoris.turnosrotativos.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<EmpleadoDTO> agregarEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {

        return new ResponseEntity<EmpleadoDTO>(empleadoService.agregarEmpleado(empleadoDTO.toEntity()),
                HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        return new ResponseEntity<>(this.empleadoService.obtenerTodosLosEmpleados(), HttpStatus.OK);
    }

    @GetMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleado(@PathVariable("empleadoId") Long id) {
        return ResponseEntity.ok(this.empleadoService.obtenerEmpleado(id));
    }

}
