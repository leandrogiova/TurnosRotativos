package com.neoris.turnosrotativos.services.Implementacion;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.BussinessException;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;
import com.neoris.turnosrotativos.services.EmpleadoService;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /*
     * Funcion agregarEmpleado
     * Recibe un empleado y va a validar que no se ingrese un numero de documento
     * existente ni un mail que ya exista en la base de datos.
     * Se va a verificar que la fecha de nacimiento, que el empleado sea meyor a
     * 18años
     * Se va a verificar que no se ingresen fechas posteriores a la actual.
     * Se va a verificar que el campo nombre y apellido contengan unicamente Letras
     * El campo nombre y apellido luego se van a setear, la primer letra en
     * mayuscula y todas las demas en minusculas
     * Recibe un Empleado
     * Retorna un EmpleadoDTO
     */
    @Override
    public EmpleadoDTO agregarEmpleado(Empleado empleado) {
        if (validarDNI(empleado)) {
            throw new BussinessException("Ya existe un empleado con el documento ingresado.", HttpStatus.CONFLICT);
        }

        if (validarEmail(empleado)) {
            throw new BussinessException("Ya existe un empleado con ese mail ingresado.", HttpStatus.CONFLICT);
        }

        if (!mayorDeEdad(empleado)) {
            throw new BussinessException("La edad del empleado no puede ser menor a 18 años", HttpStatus.BAD_REQUEST);
        }

        if (!validarFecha(empleado.getFechaNacimiento())) {
            throw new BussinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.",
                    HttpStatus.BAD_REQUEST);
        }

        if (!validarFecha(empleado.getFechaIngreso())) {
            throw new BussinessException("La fecha de ingreso no puede ser posterior al día de la fecha.",
                    HttpStatus.BAD_REQUEST);
        }

        if (!validarNombreApellido(empleado.getNombre())) {
            throw new BussinessException("Solo se permiten letras en el campo 'nombre'", HttpStatus.BAD_REQUEST);
        }

        if (!validarNombreApellido(empleado.getApellido())) {
            throw new BussinessException("Solo se permiten letras en el campo 'apellido'", HttpStatus.BAD_REQUEST);
        }

        empleado.setNombre(empleado.setearNombreApellido(empleado.getNombre()));
        empleado.setApellido(empleado.setearNombreApellido(empleado.getApellido()));

        empleado.setearfechaDeCreacion();
        empleadoRepository.save(empleado);

        return empleado.toEmpleadoDTO();
    }

    /*
     * Funcion obtenerTodosLosEmpleados
     * Retorna una lista con todos los empleados en la base de datos
     */
    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll()
                .stream()
                .map(Empleado::toEmpleadoDTO_Static)
                .collect(Collectors.toList());
    }

    /*
     * Funcion obtenerEmpleado
     * Busca en la base de datos un empleado atraves del id.
     * Recibe Long id.
     * Retorna al empleado o lanza una excepción si el id no se encuentraen la base
     * de datos
     */
    @Override
    public EmpleadoDTO obtenerEmpleado(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);

        if (empleado.isPresent()) {
            return empleado.get().toEmpleadoDTO();
        } else {
            throw new BussinessException("No se encontró el empleado con Id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /*
     * funcion actualizarEmpleado
     * Primero se va a buscar el id en la base de datos si el id no corresponde a
     * ningun usuario
     * se lanza una excepción.
     * Luego se verifica que el campo documento que se quiera modificar, no se
     * ingrese un documento
     * de otro empleado ya ingresado, se hace lo mismo con el mail
     * Ya que ningun dni y mail se puede repetir en la base de datos
     * Luego se verifica que la fecha de nacimiento sea mayor a 18años y que las
     * fechas
     * no sean posteriores a la actual
     * Se verifica que el campo nombre y apellido se ingresen unicamente letras
     * Luego se setea los campos correspondientes.
     * Los campos nombre y apellido se setearan con la primer letra Mayuscula y las
     * siguientes en minusculas
     * Recibe un id de un empleado y el empleadoDTO
     * Retorna un tipo EmpleadoDTO
     */
    @Override
    public EmpleadoDTO actualizarEmpleado(Long empleadoId, EmpleadoDTO empleadoDTO) {

        Empleado empleadoExistente = empleadoRepository.findById(empleadoId).orElse(null);
        if (empleadoExistente == null) {
            throw new BussinessException("Empleado no encontrado con ID: " + empleadoId, HttpStatus.NOT_FOUND);
        }

        // Verificar si otro empleado ya tiene el mismo nroDocumento o email (excluyendo
        // el empleado actual)
        if (empleadoRepository.existsByNroDocumentoAndIdNot(empleadoDTO.getNroDocumento(), empleadoId)) {
            throw new BussinessException("Ya existe un empleado con el documento ingresado", HttpStatus.CONFLICT);
        }

        if (empleadoRepository.existsByEmailAndIdNot(empleadoDTO.getEmail(), empleadoId)) {
            throw new BussinessException("Ya existe un empleado con el email ingresado.", HttpStatus.CONFLICT);
        }

        if (!mayorDeEdad(empleadoDTO.toEntity())) {
            throw new BussinessException("La edad del empleado no puede ser menor a 18 años", HttpStatus.BAD_REQUEST);
        }

        if (!validarFecha(empleadoDTO.toEntity().getFechaNacimiento())) {
            throw new BussinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.",
                    HttpStatus.BAD_REQUEST);
        }

        if (!validarFecha(empleadoDTO.toEntity().getFechaIngreso())) {
            throw new BussinessException("La fecha de ingreso no puede ser posterior al día de la fecha.",
                    HttpStatus.BAD_REQUEST);
        }

        if (!validarNombreApellido(empleadoDTO.toEntity().getNombre())) {
            throw new BussinessException("Solo se permiten letras en el campo 'nombre'", HttpStatus.BAD_REQUEST);
        }

        if (!validarNombreApellido(empleadoDTO.toEntity().getApellido())) {
            throw new BussinessException("Solo se permiten letras en el campo 'apellido'", HttpStatus.BAD_REQUEST);
        }

        empleadoExistente.setNroDocumento(empleadoDTO.getNroDocumento());

        // setea el String nombre con el String que retorna "setearNombreApellido"
        // "setearNombreApellido" toma un String y retorna ese String conel primer
        // caracte mayucula y el resto minuscula
        empleadoExistente.setApellido(empleadoExistente.setearNombreApellido(empleadoDTO.getApellido()));
        empleadoExistente.setNombre(empleadoExistente.setearNombreApellido(empleadoDTO.getNombre()));

        empleadoExistente.setEmail(empleadoDTO.getEmail());
        empleadoExistente.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleadoExistente.setFechaIngreso(empleadoDTO.getFechaIngreso());

        empleadoExistente = empleadoRepository.save(empleadoExistente);

        return empleadoExistente.toEmpleadoDTO();
    }

    /*
     * funcion Eliminar Empleado
     * Recibe un numero de id del empleado
     * Y elimina al empleado de la base de datos
     * Recibe un Long
     * No tiene ningun retorno
     */
    public void eliminarEmpleado(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            empleadoRepository.delete(empleado.get());
        } else {
            throw new BussinessException("No se encontró el empleado con Id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Funcion validarDNI
     * Recibe un empleado y valida si el dni ya exite en la base de datos
     * Retorna True si exite el mail y false si el mail no existe en la base de
     * datos
     */
    @Override
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
    @Override
    public Boolean validarEmail(Empleado empleado) {
        if (empleadoRepository.findByEmail(empleado.getEmail()).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Funcion mayorDeEdad
     * Recibe un empledo y verifica su edad
     * Retorna true si el empleado es mayor de edad o
     * false si el empleado es menor de edad
     */
    @Override
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
     * Funcion validarFecha toma una fecha de tipoi LocalDate
     * y se fija que la fecha no sea posterior a la fecha actual
     * Retorna true si la fecha es anterior a la fecha actual, es decir, se ingreso
     * una fecha correctamente
     * false si la fecha es posterior a la fecha actual.
     */
    @Override
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
    @Override
    public Boolean validarNombreApellido(String stringNombreApellido) {

        char primerCaracter = Character.toUpperCase(stringNombreApellido.charAt(0));

        String restoCadena = stringNombreApellido.substring(1).toLowerCase();

        stringNombreApellido = primerCaracter + restoCadena;

        // Verifica que el string contenga solamente letras
        for (char c : stringNombreApellido.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

}
