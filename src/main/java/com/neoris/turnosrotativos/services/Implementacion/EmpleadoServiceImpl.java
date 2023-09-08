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
        }

        if (validarEmail(empleado)) {
            throw new BussinessException("Ya existe un empleado con ese mail ingresado.");
        }

        if (!mayorDeEdad(empleado)) {
            throw new BussinessException("La edad del empleado no puede ser menor a 18 años");
        }

        if (!validarFecha(empleado.getFechaNacimiento())) {
            throw new BussinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.");
        }

        if (!validarFecha(empleado.getFechaIngreso())) {
            throw new BussinessException("La fecha de ingreso no puede ser posterior al día de la fecha.");
        }

        if (!validarNombreApellido(empleado.getNombre())) {
            throw new BussinessException("Solo se permiten letras en el campo 'nombre'");
        }

        if (!validarNombreApellido(empleado.getApellido())) {
            throw new BussinessException("Solo se permiten letras en el campo 'apellido'");
        }

        // Realiza los cambios necesarios en nombre y apellido antes de guardar
        empleado.setNombre(empleado.setearNombreApellido(empleado.getNombre()));
        empleado.setApellido(empleado.setearNombreApellido(empleado.getApellido()));

        // Realiza el proceso de creación del empleado
        empleado.setearfechaDeCreacion();
        empleadoRepository.save(empleado);

        return empleado.toEmpleadoDTO();
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

    /*
     * Funcion validarNombreApellido
     * Retorna true si el String contiene solamente letras
     * Ademas pone en mayusculas al primer caracter y al resto en minusculas
     * para que en la base de datos se guarde de forma homogénea
     * Recibe un String
     * Retorna true si el String contiene solo letras
     */
    public Boolean validarNombreApellido(String stringNombreApellido) {

        char primerCaracter = Character.toUpperCase(stringNombreApellido.charAt(0));

        String restoCadena = stringNombreApellido.substring(1).toLowerCase();

        stringNombreApellido = primerCaracter + restoCadena;

        // Verificamos si la cadena resultante contiene solo letras
        for (char c : stringNombreApellido.toCharArray()) {
            if (!Character.isLetter(c)) {
                System.out.println(stringNombreApellido + " no es válido.");
                return false;
            }
        }

        // Si hemos llegado hasta aquí, la cadena es válida y ha sido formateada
        // correctamente
        System.out.println(stringNombreApellido + " es válido.");
        return true;
    }

}
