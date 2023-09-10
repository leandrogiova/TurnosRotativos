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
     * 
     * 
     * 
     * 
     * 
     */
    public JornadaDTO agregarJornada(Jornada jornada) {

        LocalDate[] fechasDeLaSemana = new LocalDate[2];

        Empleado empleado = validarIdEmpleado(jornada.getIdEmpleado());

        Concepto concepto = validarConcepto(jornada.getIdConcepto(), jornada.getHorasTrabajadas());

        validarHoras(concepto, jornada.getHorasTrabajadas());

        jornada.setNroDocumento(empleado.getNroDocumento());
        jornada.setNombreCompleto(empleado.getNombre() + " " + empleado.getApellido());
        jornada.setIdConcepto(concepto.getId());
        jornada.setNombreConcepto(concepto.getNombre());

        if (jornadaRepository.existsByNroDocumentoAndFechaAndIdConcepto(jornada.getNroDocumento(), jornada.getFecha(),
                jornada.getIdConcepto())) {
            throw new BussinessException(
                    "El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.",
                    HttpStatus.BAD_REQUEST);
        }

        validarCantidadDeHorasPorDiaDeUnEmpleado(jornada);

        validarDiaLibre(jornada);
        validarDiaLibre2(jornada);
        fechasDeLaSemana = calcularInicioYFinSemana(jornada.getFecha());

        validarCantidadDeHorasSemanales(jornada, fechasDeLaSemana);
        validarCantidadDeTurnosSemanales(jornada, fechasDeLaSemana);

        jornadaRepository.save(jornada);

        return jornada.toJornadaDTO();

    }

    /*
     * 
     * La semaan empieza el domingo
     * La semana finaliza el sabado
     * Retorna el primer parametro la fecha de inicio de la semana
     * y el segundo parametro la fecha de fin de la semana
     */
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

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\nFecha: " + fecha);
        System.out.println("Fecha de inicio de semana: " + fechaInicioSemana);
        System.out.println("Fecha de fin de semana: " + fechaFinSemana + "\n");

        fechas[0] = fechaInicioSemana;
        fechas[1] = fechaFinSemana;

        return fechas;

    }

    /*
     * Recibe una jornada y un array de fechas
     * la posicion 0 es la fecha de inicio y la posicion 1 del array es la fecha de
     * final de semana
     */
    public void validarCantidadDeHorasSemanales(Jornada jornada, LocalDate[] fechas) {

        if (jornada.getIdConcepto() != 3) {
            Integer contadorDeHoras = jornada.getHorasTrabajadas();

            List<Jornada> joranadas = jornadaRepository.findByNroDocumentoAndFechaBetween(jornada.getNroDocumento(),
                    fechas[0], fechas[1]);

            for (Jornada j : joranadas) {

                if (j.getIdConcepto() != 3) {
                    contadorDeHoras += j.getHorasTrabajadas();
                    System.out.println("\n-------Bcle--contadorDeHoras: " + contadorDeHoras);
                }

            }
            System.out.println("contadorDeHoras: " + contadorDeHoras + "\n\n\n\n\n");
            if (contadorDeHoras > 48) {
                throw new BussinessException("El empleado ingresado supera las 48 horas semanales.",
                        HttpStatus.BAD_REQUEST);
            }
        }

    }

    /*
     * 
     */
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
            else if (j.getIdConcepto() == 3)
                contadorTurnosDiasLibres++;
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
     */
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
     * 
     */
    public void validarDiaLibre2(Jornada jornada) {
        List<Jornada> jornad_ = jornadaRepository.findByNroDocumentoAndFecha_(jornada.getNroDocumento(),
                jornada.getFecha());
        if (!jornad_.isEmpty()) {
            if (jornad_.get(0).getIdConcepto() == 3) {
                throw new BussinessException(
                        "El empleado ya tiene cargado un dia libre.",
                        HttpStatus.BAD_REQUEST);
            }
        }

    }

    /*   
    * 
     */
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
     * Retorna una lista con todos las jornadas en la base de datos
     */
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
     * Retorna al empleado o lanza una excepción
     */
    public Empleado validarIdEmpleado(Long idEmpleado) {
        System.out.println("\n\n\n\n\n\n\n\n\nEmpleado id: " + idEmpleado);

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
     * Retorna al concepto o lanza una excepción
     */
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
     * Funcion
     */
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
    public String setearNombreCompletoDelEmpleado(Long numeroDocumento) {

        Optional<Empleado> empleado = empleadoRepository.findByNroDocumento(numeroDocumento);
        if (empleado.isPresent()) {
            return empleado.get().getNombre() + " " + empleado.get().getApellido();
        } else {
            return null;
        }
    }

}
