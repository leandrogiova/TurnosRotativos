package com.neoris.turnosrotativos.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.BussinessException;
import com.neoris.turnosrotativos.services.Implementacion.EmpleadoServiceImpl;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoServiceImpl empleadoService;

    /*
     * 
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EmpleadoDTO> agregarEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {

        return new ResponseEntity<EmpleadoDTO>(empleadoService.agregarEmpleado(empleadoDTO.toEntity()),
                HttpStatus.CREATED);
    }

    /*
     * 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        return new ResponseEntity<>(this.empleadoService.obtenerTodosLosEmpleados(), HttpStatus.OK);
    }

    /*
     * 
     */
    @GetMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleado(@PathVariable("empleadoId") String stringId) {
        Long id_;

        try {
            id_ = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            throw new BussinessException("El id del empleado en la URI contiene caracteres.", HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(this.empleadoService.obtenerEmpleado(id_));
    }

    /*
     * Funcion actualiarEmpleado
     * Va a actualizar un empleado con sus respectivos cambios
     * Recibe un String y se valida que se le pase unicamente un Long, en otro caso
     * lanza una excepción
     * Llama al Service y le pasa el id y el empleado a actualiazar
     * Recibe un String con el id y un EmpleadoDTO
     * Retorna un ResponseEntity de EmpleadoDTO con un estado OK 200
     */
    @PutMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@PathVariable("empleadoId") String stringId,
            @Valid @RequestBody EmpleadoDTO empleadoDTO) {

        Long id_;

        try {
            id_ = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            throw new BussinessException("El id del empleado en la URI contiene caracteres.", HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(this.empleadoService.actualizarEmpleado(id_, empleadoDTO));

    }

    /*
     * 
     */
    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<HttpStatus> eliminarEmpleado(@PathVariable("empleadoId") String stringId) {
        Long id_;

        try {
            id_ = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            throw new BussinessException("El id del empleado en la URI contiene caracteres.", HttpStatus.CONFLICT);
        }
        this.empleadoService.eliminarEmpleado(id_);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
