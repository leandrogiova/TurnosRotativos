package com.neoris.turnosrotativos.services.Implementacion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.exceptions.BussinessException;
import com.neoris.turnosrotativos.repositorys.ConceptoRepository;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;
import com.neoris.turnosrotativos.repositorys.JornadaRepository;
import com.neoris.turnosrotativos.services.JornadaService;

@Service
public class JornadaServiceImpl implements JornadaService {

    @Autowired
    private JornadaRepository jornadaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ConceptoRepository conceptoRepository;

    /*
     * Funcion agregarJornada
     * Recibe una jornada y va a validar que se ingrese una jornada correctamente.
     * 1)Validando que el id del empleado que se ingresa corresponda a un empleado.
     * 2)Validando que el id del concepto sea de un concepto
     * 3)Se va a verificar que la cantidad de horas trabajadas sea entre el
     * rango de horas que permite el concepto
     * Luego, setea los campos de jornada, para que la jornada contenga los datos
     * correspondientes que se necesitan.
     * 4) Se va a validar que un empleado no pueda tener el mismo concepto en el
     * mismo dia
     * 5) Se va a validar que un empleado no pueda tener mas de 12hs trabajadas en
     * un dia
     * 6) Se va a validar que un empleado pueda o no cargar un dia libre
     * 7) Se va a validar que un empleado no pueda cargar un dia libre si ya cargo
     * otro concepto.
     * Validacion 6 y 7 son complementarias
     * //
     * calcularInicioYFinSemana calcula el inicio y final de una semana
     * Recibe una fecha LocalDate y retorna un array de dos fechas 'LocalDate[2]'
     * con el inicio de la semana y el final de una semana
     * LocalDate[0] = fecha inicio de semana
     * LocalDate[1] = fecha de final de semana
     * //
     * Luego se valida la cantidad de horas semanales de un empleado, que esa
     * cantidad de hs sea menor a 48hs
     * Se valida la cantidad de turnos que puede tener un empleado por semana
     * 3 turnos Extra, 5 Turnos normales, 2 Dias libres
     * Si se cumplen todas las validaciones se guarda la jornaa en la base de datos
     * Recibe un tipo Jornada
     * Retorna un tipo JornadaDTO
     */
    @Override
    public JornadaDTO agregarJornada(Jornada jornada) {

        LocalDate[] fechasDeLaSemana = new LocalDate[2];

        Empleado empleado = validarIdEmpleado(jornada.getIdEmpleado());

        Concepto concepto = validarConcepto(jornada.getIdConcepto(), jornada.getHorasTrabajadas());

        validarHoras(concepto, jornada.getHorasTrabajadas());

        jornada.setNroDocumento(empleado.getNroDocumento());
        jornada.setNombreCompleto(empleado.getNombre() + " " + empleado.getApellido());
        jornada.setIdConcepto(concepto.getId());
        jornada.setNombreConcepto(concepto.getNombre());

        validarEmpleadoConElMismoConcepto(jornada);
        validarCantidadDeHorasPorDiaDeUnEmpleado(jornada);

        validarDiaLibre(jornada);
        validarConceptoFecha(jornada);
        fechasDeLaSemana = calcularInicioYFinSemana(jornada.getFecha());

        validarCantidadDeHorasSemanales(jornada, fechasDeLaSemana);
        validarCantidadDeTurnosSemanales(jornada, fechasDeLaSemana);

        jornadaRepository.save(jornada);

        return jornada.toJornadaDTO();

    }

    /*
     * Funcion validarEmpleadoConElMismoConcepto
     * Valida que un empleado no puedo cargar tipos de conceptos iguales
     * Es decir, un empleado, no puede en un mismo dia tener dos Trunos Normales. Lo
     * mismo con TurnosExtra, y Dia Libres
     * Lanza una Exception si el empleado ya tiene registrado una jornada con este
     * concepto en una fecha
     * Recibe una Jornada
     * No tiene ningun tipo de retorno
     */
    @Override
    public void validarEmpleadoConElMismoConcepto(Jornada jornada) {
        if (jornadaRepository.existsByNroDocumentoAndFechaAndIdConcepto(jornada.getNroDocumento(), jornada.getFecha(),
                jornada.getIdConcepto())) {
            throw new BussinessException(
                    "El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.",
                    HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Funcion calcularInicioYFinSemana
     * Recibe una fecha LocalDate y va retornar dos fechas
     * Una fecha del inicio de semana que comienza el 'domingo'
     * Y una fecah de final de semana que terminar en el 'sababo'
     * Ejemplo: fecha:2023-09-05, fechaInicio:2023-09-03, fechaFinal:2023-09-09
     * La semaan empieza el domingo
     * La semana finaliza el sabado
     * Recibe un LocalDate
     * Retorna un array de fechas de tipo LocalDate
     * Retorna el primer parametro la fecha de inicio de la semana
     * y el segundo parametro la fecha de fin de la semanas
     */
    @Override
    public LocalDate[] calcularInicioYFinSemana(LocalDate fecha) {
        LocalDate[] fechas = new LocalDate[2];
        // Calcula el inicio de semana (domingo)
        LocalDate fechaInicioSemana = fecha;
        while (fechaInicioSemana.getDayOfWeek() != DayOfWeek.SUNDAY) {
            fechaInicioSemana = fechaInicioSemana.minusDays(1);
        }

        // Calcula el final de semana (sábado)
        LocalDate fechaFinSemana = fecha;
        while (fechaFinSemana.getDayOfWeek() != DayOfWeek.SATURDAY) {
            fechaFinSemana = fechaFinSemana.plusDays(1);
        }

        // System.out.println("\nFecha: " + fecha);
        // System.out.println("Fecha de inicio de semana: " + fechaInicioSemana);
        // System.out.println("Fecha de fin de semana: " + fechaFinSemana + "\n");

        fechas[0] = fechaInicioSemana;
        fechas[1] = fechaFinSemana;

        return fechas;

    }

    /*
     * Funcion validarCantidadDeHorasSemanales
     * Recibe una jornada y un array de fechas
     * En el array de fechas, la posicion 0 es la fecha de inicio y la posicion 1
     * del array es la fecha de
     * final de semana.
     * valida que un empleado no pueda superar las 48hs semanales trabajadas
     * Lanza una Exception si se cumple esta condicion
     * Recibe una Jornada, y un LocalData[2]
     * No tiene ningun tipo de retorno
     */
    @Override
    public void validarCantidadDeHorasSemanales(Jornada jornada, LocalDate[] fechas) {

        if (jornada.getIdConcepto() != 3) {
            Integer contadorDeHoras = jornada.getHorasTrabajadas();

            List<Jornada> joranadas = jornadaRepository.findByNroDocumentoAndFechaBetween(jornada.getNroDocumento(),
                    fechas[0], fechas[1]);

            for (Jornada j : joranadas) {

                if (j.getIdConcepto() != 3) {
                    contadorDeHoras += j.getHorasTrabajadas();
                }

            }
            if (contadorDeHoras > 48) {
                throw new BussinessException("El empleado ingresado supera las 48 horas semanales.",
                        HttpStatus.BAD_REQUEST);
            }
        }

    }

    /*
     * Funcion validarCantidadDeTurnosSemanales
     * Valida la cantidad de turnos que puede tomar un empleado
     * 5 turnos Normales, 3 turnos extra, 2 Dia Libres
     * Lanza una Exceptio si sucede esos casos
     * Recibe una jornada y un array de fechas LocalDate
     * No tiene ningun tipo de retorno
     */
    @Override
    public void validarCantidadDeTurnosSemanales(Jornada jornada, LocalDate[] fechas) {
        Integer contadorTurnosExtra = 0;
        Integer contadorTurnosNormales = 0;
        Integer contadorTurnosDiasLibres = 0;

        List<Jornada> joranadas = jornadaRepository.findByNroDocumentoAndFechaBetween(jornada.getNroDocumento(),
                fechas[0], fechas[1]);

        for (Jornada j : joranadas) {

            if (j.getIdConcepto() == 1)
                contadorTurnosNormales++;
            else if (j.getIdConcepto() == 2)
                contadorTurnosExtra++;
            else if (j.getIdConcepto() == 3) {
                contadorTurnosDiasLibres++;
            }

        }

        if (jornada.getIdConcepto() == 1 && contadorTurnosNormales == 5) {
            throw new BussinessException("El empleado ingresado ya cuenta con 5 turnos normales esta semana.",
                    HttpStatus.BAD_REQUEST);
        } else if (jornada.getIdConcepto() == 2 && contadorTurnosExtra == 3) {
            throw new BussinessException("El empleado ingresado ya cuenta con 3 turnos extra esta semana.",
                    HttpStatus.BAD_REQUEST);
        } else if (jornada.getIdConcepto() == 3 && contadorTurnosDiasLibres == 2) {
            throw new BussinessException("El empleado no cuenta con más dias libres esta semana.",
                    HttpStatus.BAD_REQUEST);
        }

    }

    /*
     * Funcion validarCantidadDeHorasPorDiaDeUnEmpleado
     * Valida que un empleajo no pueda trabajar mas de 12hs en un mismo dia
     * Lanza una Exception si un empleado carga mas de 12hs en un mismo dia
     * Recibe una jornada
     * No tiene ningun retorno
     */
    @Override
    public void validarCantidadDeHorasPorDiaDeUnEmpleado(Jornada jornada) {
        Integer cantidadDeHorasTrabajadasPorDia = 0;
        if (jornada.getIdConcepto() != 3) {
            List<Jornada> jornad_ = jornadaRepository.findByNroDocumentoAndFecha_(jornada.getNroDocumento(),
                    jornada.getFecha());
            if (!jornad_.isEmpty()) {

                if (jornad_.get(0).getIdConcepto() != 3) {
                    cantidadDeHorasTrabajadasPorDia = jornada.getHorasTrabajadas()
                            + jornad_.get(0).getHorasTrabajadas();
                }

                if (cantidadDeHorasTrabajadasPorDia > 12) {
                    throw new BussinessException(
                            "El empleado no puede cargar más de 12 horas trabajadas en un dia",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }
    }

    /*
     * Funcion validarConceptoFecha
     * Valida que un empleado no pueda cargar algun concepto ya que se valido que
     * tiene cargado un 'DiaLibre' en esa fecha
     * Lanza una Exception si encuentra que tiene cargado un Concepto dia libre y se
     * quiere cargar otro concepto en esa misma fecha
     * REcibe una Jornada
     * No tiene ningun tipo de retorno
     */
    @Override
    public void validarConceptoFecha(Jornada jornada) {
        List<Jornada> jornad_ = jornadaRepository.findByNroDocumentoAndFecha_(jornada.getNroDocumento(),
                jornada.getFecha());
        if (!jornad_.isEmpty()) {
            if (jornad_.get(0).getIdConcepto() == 3) {
                throw new BussinessException(
                        "El empleado ingresado cuenta con un día libre en esa fecha.",
                        HttpStatus.BAD_REQUEST);
            }
        }

    }

    /*
     * Funcion validarDiaLibre
     * Valida que un empleado no pueda cargar un dia libre ya cargo otro concepto
     * ese mismo dia.
     * Lanza una Excepcion si un empleado quiere cargar un dia libre y tiene cargado
     * otro concepto el mismo dia
     * Recibe una Jornada
     * No tiene ningun tipo de retorno.
     */
    @Override
    public void validarDiaLibre(Jornada jornada) {

        if (jornada.getIdConcepto() == 3) {

            List<Jornada> jornad_ = jornadaRepository.findByNroDocumentoAndFecha_(jornada.getNroDocumento(),
                    jornada.getFecha());

            if (!jornad_.isEmpty()) {
                throw new BussinessException(
                        "El empleado no puede cargar Dia Libre si cargo un turno previmente para la fecha ingresada",
                        HttpStatus.BAD_REQUEST);
            }

        }
    }

    /*
     * Funcion obtenerTodosLasJornadas
     * No recibe ningun paramtro
     * Retorna una lista con todos las jornadas en la base de datos
     */
    @Override
    public List<JornadaDTO> obtenerTodosLasJornadas() {
        return jornadaRepository.findAll()
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toJornadaDTO();
                })
                .collect(Collectors.toList());

    }

    /*
     * Funcion obtenerJornadasPorDocumentoYFecha
     * Recibe como parametro un numero de documento de un empleado y una fecha
     * Setea el nombre completo del empleado y el nombre del concepto
     * Recibe como parametro un Long nroDocumento y un LocalDate fecha
     * Retorna una lista con todas las jornadas de ese empleado en esa fecha
     */
    @Override
    public List<JornadaDTO> obtenerJornadasPorDocumentoYFecha(Long nroDocumento, LocalDate fecha) {
        return jornadaRepository.findByNroDocumentoAndFecha(nroDocumento, fecha)
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toJornadaDTO();
                })
                .collect(Collectors.toList());
    }

    /*
     * Funcion obtenerJornadasPorDocumento
     * Recibe como parametro un numero de documento de un empleado
     * Setea el nombre completo del empleado y el nombre del concepto
     * Recibe como parametro un Long nroDocumento
     * Retorna una lista con todas las jornadas de ese empleado
     */
    @Override
    public List<JornadaDTO> obtenerJornadasPorDocumento(Long nroDocumento) {
        return jornadaRepository.findByNroDocumento(nroDocumento)
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toJornadaDTO();
                })
                .collect(Collectors.toList());
    }

    /*
     * Funcion obtenerJornadasPorFecha
     * Recibe como parametro una fecha LocalDate
     * Setea el nombre completo del empleado y el nombre del concepto
     * Recibe como parametro de una fecha LocalDate
     * Retorna una lista con todas las jornadas de ese empleado
     */
    @Override
    public List<JornadaDTO> obtenerJornadasPorFecha(LocalDate fecha) {
        return jornadaRepository.findByFecha(fecha)
                .stream()
                .map(jornada -> {
                    jornada.setNombreCompleto(setearNombreCompletoDelEmpleado(jornada.getNroDocumento()));
                    jornada.setNombreConcepto(setearNombreDelConcepto(jornada.getIdConcepto()));

                    return jornada.toJornadaDTO();
                })
                .collect(Collectors.toList());
    }

    /*
     * Funcion validarIdEmpleado
     * Recibe un Long de un id de un empleado
     * Retorna al empleado o lanza una excepción si el idEmpleado no existe en la
     * base de datos
     */
    @Override
    public Empleado validarIdEmpleado(Long idEmpleado) {
        Optional<Empleado> empleado = empleadoRepository.findById(idEmpleado);

        if (empleado.isPresent()) {
            return empleado.get();
        } else {
            throw new BussinessException("No existe el empleado ingresado", HttpStatus.NOT_FOUND);
        }

    }

    /*
     * Funcion validarIdConcepto
     * Recibe un Integer de un id de un concepto
     * Retorna al concepto o lanza una excepción si el idConcepto no existe en la
     * base de datos
     */
    @Override
    public Concepto validarConcepto(Integer idConcepto, Integer horasTrabajadas) {
        Optional<Concepto> concepto = conceptoRepository.findById(idConcepto);

        if (concepto.isPresent()) {

            if ((concepto.get().getId() == 1 && horasTrabajadas == null)
                    || (concepto.get().getId() == 2 && horasTrabajadas == null)) {

                throw new BussinessException("hs Trabajadas es obligatorio para el concepto ingresado",
                        HttpStatus.BAD_REQUEST);
            }

            else if (concepto.get().getId() == 3 && horasTrabajadas != null) {
                throw new BussinessException("El concepto ingresado no requiere el ingreso de hs Trabajadas",
                        HttpStatus.BAD_REQUEST);
            }

            return concepto.get();

        } else {
            throw new BussinessException("No existe el concepto ingresado", HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Funcion validarHoras
     * Recibe un conpeto y las horas que va a trabajar el empleado
     * Se va a validar que las horasTrabajadas esten en el rango de horas
     * que permite el concepto.
     * Recibe 'Concepto', y un 'Integer' de 'horasTrabajadas'
     * No tiene ningun tiopo de retorno.
     */
    @Override
    public void validarHoras(Concepto concepto, Integer horasTrabajadas) {

        if (concepto.getId() != 3
                && ((horasTrabajadas > concepto.getHsMaximo()) || horasTrabajadas < concepto.getHsMinimo())) {
            throw new BussinessException("El rango de horas que se puede cargar para este concepto es de "
                    + concepto.getHsMinimo() + " - " + concepto.getHsMaximo(), HttpStatus.BAD_REQUEST);
        }

    }

    /*
     * Funcion setearNombreDelConcepto
     * Recibe un id de un Concepto
     * Retorna el nombre de ese Concepto que recibe como parametro
     */
    @Override
    public String setearNombreDelConcepto(Integer idConcepto) {

        Optional<Concepto> concepto = conceptoRepository.findById(idConcepto);

        if (concepto.isPresent()) {
            return concepto.get().getNombre();
        } else {
            return null;
        }

    }

    /*
     * Funcion setearNombreDelConcepto
     * Recibe un id de un Concepto
     * Retorna el nombre de ese Concepto que recibe como parametro
     */
    @Override
    public String setearNombreCompletoDelEmpleado(Long numeroDocumento) {

        Optional<Empleado> empleado = empleadoRepository.findByNroDocumento(numeroDocumento);
        if (empleado.isPresent()) {
            return empleado.get().getNombre() + " " + empleado.get().getApellido();
        } else {
            return null;
        }
    }

}
