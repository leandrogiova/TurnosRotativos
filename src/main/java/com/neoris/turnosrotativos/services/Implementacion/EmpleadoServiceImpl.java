package com.neoris.turnosrotativos.services.Implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.BussinessException;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public EmpleadoDTO agregarEmpleado(Empleado empleado) {
        // TODO agregar validar mail
        if (validarDNI(empleado)) {
            throw new BussinessException("Ya existe un empleado con el documento ingresado.");
        } else {
            empleado.setearfechaDeCreacion();
            empleadoRepository.save(empleado);
            return empleado.toEmpleadoDTO();
        }

    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();

    }

    public EmpleadoDTO obtenerEmpleado(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);

        if (empleado.isPresent()) {
            return empleado.get().toEmpleadoDTO();
        } else {
            return null;
        }
    }

    /*
     * Funcion validarDNI
     * Recibe un empleado y valida si el dni ya exite en la base de datos
     * Retorna True si exite el mail y false si el mail no existe en la base de
     * datos
     */
    public Boolean validarDNI(Empleado empleado) {
        if (empleadoRepository.findByNroDocumento(empleado.getNroDocumento()).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Funcion validarEmail
     * Recibe un empleado y valida si el dni ya exite en la base de datos
     * Retorna True si exite el mail y false si el mail no existe en la base de
     * datos
     */
    public Boolean validarEmail(Empleado empleado) {
        if (empleadoRepository.findByEmail(empleado.getEmail()).isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
