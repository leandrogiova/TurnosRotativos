package com.neoris.turnosrotativos.services;

import java.time.LocalDate;
import java.util.List;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.entities.Empleado;

public interface EmpleadoService {

    public EmpleadoDTO agregarEmpleado(Empleado empleado);

    public List<EmpleadoDTO> obtenerTodosLosEmpleados();

    public EmpleadoDTO obtenerEmpleado(Long id);

    public EmpleadoDTO actualizarEmpleado(Long empleadoId, EmpleadoDTO empleadoDTO);

    public void eliminarEmpleado(Long id);

    // Metodos para validar y demás funcionalidades

    public Boolean validarDNI(Empleado empleado);

    public Boolean validarEmail(Empleado empleado);

    public Boolean mayorDeEdad(Empleado empleado);

    public Boolean validarFecha(LocalDate fecha);

    public Boolean validarNombreApellido(String stringNombreApellido);

}
