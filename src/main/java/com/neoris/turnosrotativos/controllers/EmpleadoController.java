package com.neoris.turnosrotativos.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.exceptions.BussinessException;
import com.neoris.turnosrotativos.services.Implementacion.EmpleadoServiceImpl;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoServiceImpl empleadoService;

    /*
     * Funcion agregarEmpleado
     * Agrega un empleado a la base de datos
     * Llamando al service pasandole un EmpleadoDTO
     * Recibe como parametro un empleadoDTO
     * Retorna un ResponseEntity con la entidad empleadoDTO, retorna status 201
     * CREATED si sale todo ok
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EmpleadoDTO> agregarEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {

        return new ResponseEntity<EmpleadoDTO>(empleadoService.agregarEmpleado(empleadoDTO.toEntity()),
                HttpStatus.CREATED);
    }

    /*
     * Funcion obtenerTodosLosEmpleados
     * Obtiene todos los empleados de la base de datos
     * No recibe parametros
     * Retorna ResponseEntity con una lista List<EmpleadoDTO>. Retorna 200 OK si
     * sale todo OK
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EmpleadoDTO>> obtenerTodosLosEmpleados() {
        return new ResponseEntity<>(this.empleadoService.obtenerTodosLosEmpleados(), HttpStatus.OK);
    }

    /*
     * Funcion obtenerEmpleado
     * Obtiene un empleado a través de su ID
     * Recibe un String id y lo setea a un tipo Long
     * El ID del empleado es de tipo Long
     * Retorna ResponseEntity<EmpleadoDTO> si se encontro al empleado.
     * Retorna 200 OK
     * Si no se encuentra al empleado se lanzara una Exception, retorna NOT_FOUND
     * 404
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
     * Funcion eliminarEmpleado
     * Elimina un empleado de la base de datos
     * Recibe el id del empleado a eliminar. Recibe un String y va a setear el
     * Long del id del Empleado
     * Retorna un ResponseEntity<HttpStatus> 204 si sale todo bien
     * o NOT_FOUND 404 si el id no existe en la base de datos
     */
    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable("empleadoId") String stringId) {
        Long id_;

        try {
            id_ = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            throw new BussinessException("El id del empleado en la URI contiene caracteres.", HttpStatus.CONFLICT);
        }
        this.empleadoService.eliminarEmpleado(id_);

        return new ResponseEntity<>("El empleado fue eliminado con éxito", HttpStatus.OK);
    }

}
