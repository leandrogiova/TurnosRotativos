package com.neoris.turnosrotativos.services.Implementacion;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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

        empleado.setNombre(empleado.setearNombreApellido(empleado.getNombre()));
        empleado.setApellido(empleado.setearNombreApellido(empleado.getApellido()));

        empleado.setearfechaDeCreacion();
        empleadoRepository.save(empleado);

        return empleado.toEmpleadoDTO();
    }

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
            throw new BussinessException("No se encontró el empleado con Id: " + id);
        }
    }

    /*
     * funcion actualizarEmpleado
     * Recibe un id de un empleado y un empleado
     * Retorna un empleadoDTO
     */
    public EmpleadoDTO actualizarEmpleado2(Long id, EmpleadoDTO empleadoDTO) {

        Optional<Empleado> empleadoFind = empleadoRepository.findById(id);
        System.out.println("\n\n\n\n\nfechdeDeCreacion: " + empleadoDTO.getFechaCreacion());
        if (empleadoFind.isPresent()) {
            empleadoDTO.setId(empleadoFind.get().getId());

            Empleado empleado_ = empleadoDTO.toEntity();

            // valido todas las excepciones
            if (validarDNI(empleado_)) {
                throw new BussinessException("Ya existe un empleado con el documento ingresado.");
            }
            if (validarEmail(empleado_)) {
                throw new BussinessException("Ya existe un empleado con ese mail ingresado.");
            }

            if (!mayorDeEdad(empleado_)) {
                throw new BussinessException("La edad del empleado no puede ser menor a 18 años");
            }

            if (!validarFecha(empleado_.getFechaNacimiento())) {
                throw new BussinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.");
            }

            if (!validarFecha(empleado_.getFechaIngreso())) {
                throw new BussinessException("La fecha de ingreso no puede ser posterior al día de la fecha.");
            }

            if (!validarNombreApellido(empleado_.getNombre())) {
                throw new BussinessException("Solo se permiten letras en el campo 'nombre'");
            }

            if (!validarNombreApellido(empleado_.getApellido())) {
                throw new BussinessException("Solo se permiten letras en el campo 'apellido'");
            }

            empleado_.setNombre(empleado_.setearNombreApellido(empleado_.getNombre()));
            empleado_.setApellido(empleado_.setearNombreApellido(empleado_.getApellido()));
            empleado_.setFechaCreacion(empleadoDTO.getFechaCreacion());
            empleado_ = empleadoRepository.save(empleado_);
            return empleado_.toEmpleadoDTO();

        } else {
            throw new BussinessException("El id que quiere modificar no existe");
        }
    }

    public EmpleadoDTO actualizarEmpleado3(Long empleadoId, EmpleadoDTO empleadoDTO) {

        Empleado empleado_ = empleadoDTO.toEntity();

        Empleado empleadoExistente = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID: " + empleadoId));

        // Verificar si otro empleado ya tiene el mismo nroDocumento o email (excluyendo
        // el empleado actual)
        if (empleadoRepository.existsByNroDocumentoAndIdNot(empleadoDTO.getNroDocumento(), empleadoId)) {
            throw new BussinessException("Ese nroDocumento ya está en uso por otro empleado.");
        }

        if (empleadoRepository.existsByEmailAndIdNot(empleadoDTO.getEmail(), empleadoId)) {
            throw new BussinessException("Ese email ya está en uso por otro empleado.");
        }

        empleado_.setId(empleadoDTO.getId());
        empleado_.setNombre(empleado_.setearNombreApellido(empleado_.getNombre()));
        empleado_.setApellido(empleado_.setearNombreApellido(empleado_.getApellido()));
        empleado_.setFechaCreacion(empleadoDTO.getFechaCreacion());
        empleado_ = empleadoRepository.save(empleado_);
        return empleado_.toEmpleadoDTO();
    }

    /*
     * funcion actualizarEmpleado
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * Recibe un id de un empleado y el empleado
     */
    public EmpleadoDTO actualizarEmpleado(Long empleadoId, EmpleadoDTO empleadoDTO) {
        // Obtener el empleado existente por ID

        // TODO VER EL TEMA CUANDO SE INGRESA UN ID NO REGISTRADO
        // Empleado empleadoExistente = empleadoRepository.findById(empleadoId)
        // .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con
        // ID: " + empleadoId));

        Empleado empleadoExistente = empleadoRepository.findById(empleadoId).orElse(null);
        if (empleadoExistente == null) {
            throw new BussinessException("Empleado no encontrado con ID: " + empleadoId);
        }

        // Verificar si otro empleado ya tiene el mismo nroDocumento o email (excluyendo
        // el empleado actual)
        if (empleadoRepository.existsByNroDocumentoAndIdNot(empleadoDTO.getNroDocumento(), empleadoId)) {
            throw new BussinessException("Ese nroDocumento ya está en uso por otro empleado.");
        }

        if (empleadoRepository.existsByEmailAndIdNot(empleadoDTO.getEmail(), empleadoId)) {
            throw new BussinessException("Ese email ya está en uso por otro empleado.");
        }

        if (!mayorDeEdad(empleadoDTO.toEntity())) {
            throw new BussinessException("La edad del empleado no puede ser menor a 18 años");
        }

        if (!validarFecha(empleadoDTO.toEntity().getFechaNacimiento())) {
            throw new BussinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.");
        }

        if (!validarFecha(empleadoDTO.toEntity().getFechaIngreso())) {
            throw new BussinessException("La fecha de ingreso no puede ser posterior al día de la fecha.");
        }

        if (!validarNombreApellido(empleadoDTO.toEntity().getNombre())) {
            throw new BussinessException("Solo se permiten letras en el campo 'nombre'");
        }

        if (!validarNombreApellido(empleadoDTO.toEntity().getApellido())) {
            throw new BussinessException("Solo se permiten letras en el campo 'apellido'");
        }

        // Actualizar los campos permitidos del empleado existente con los valores de
        // DTO
        empleadoExistente.setNroDocumento(empleadoDTO.getNroDocumento());

        // setea el String nombre con el String que retorna "setearNombreApellido"
        // "setearNombreApellido" toma un String y retorna ese String conel primer
        // caracte mayucula y el resto minuscula
        empleadoExistente.setApellido(empleadoExistente.setearNombreApellido(empleadoDTO.getApellido()));
        empleadoExistente.setNombre(empleadoExistente.setearNombreApellido(empleadoDTO.getNombre()));

        // empleadoExistente.setNombre(empleadoDTO.getNombre());
        // empleadoExistente.setApellido(empleadoDTO.getApellido());
        empleadoExistente.setEmail(empleadoDTO.getEmail());
        empleadoExistente.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleadoExistente.setFechaIngreso(empleadoDTO.getFechaIngreso());

        // Guardar los cambios en la base de datos
        empleadoExistente = empleadoRepository.save(empleadoExistente);

        return empleadoExistente.toEmpleadoDTO();
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

        // Verifica que el string contenga solamente letras
        for (char c : stringNombreApellido.toCharArray()) {
            if (!Character.isLetter(c)) {
                System.out.println(stringNombreApellido + " no es válido.");
                return false;
            }
        }

        return true;
    }

}
