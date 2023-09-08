package com.neoris.turnosrotativos.services.Implementacion;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

        if (validarDNI(empleado)) {
            throw new BussinessException("Ya existe un empleado con el documento ingresado.");
        } else {

            if (validarEmail(empleado)) {
                throw new BussinessException("Ya existe un empleado con ese mail ingresado.");
            } else {

                if (mayorDeEdad(empleado)) {

                    if (validarFecha(empleado.getFechaNacimiento())) {
                        if (validarFecha(empleado.getFechaIngreso())) {
                            empleado.setearfechaDeCreacion();
                            empleadoRepository.save(empleado);
                            return empleado.toEmpleadoDTO();
                        } else {
                            throw new BussinessException(
                                    "La fecha de ingreso no puede ser posterior al día de la fecha.");
                        }

                    } else {
                        throw new BussinessException(
                                "La fecha de nacimiento no puede ser posterior al día de la fecha.");
                    }

                } else {
                    throw new BussinessException("La edad del empleado no puede ser menor a 18 años");
                }

            }

        }

    }

    // TODO agregar validar el nombre y el apellido
    // TODO ver y revisar el get a todos los empleados y revisar el get a un
    // empledao
    // TODO agregar put, actualizar empleado
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

    /*
     * funcion mayorDeEdad
     * Recibe un empledo y verifica su edad
     * Retorna true si el empleado es mayor de edad o
     * false si el empleado es menor de edad
     */
    public Boolean mayorDeEdad(Empleado empleado) {
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(empleado.getFechaNacimiento(), fechaActual);
        if (periodo.getYears() >= 18) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * funcion validarFecha toma una fecha de tipoi LocalDate
     * y se fija que la fecha no sea posterior a la fecha actual
     * Retorna true si la fecha es anterior a la fecha actual, es decir, se ingreso
     * una fecha correctamente
     * false si la fecha es posterior a la fecha actual.
     */
    public Boolean validarFecha(LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        return !fecha.isAfter(fechaActual);
    }

}
