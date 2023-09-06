package com.neoris.turnosrotativos.services.Implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.neoris.turnosrotativos.dto.EmpleadoDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public void agregarEmpleado(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    public List<Empleado> obtenerTodosLosEmpleados() {

        if (empleadoRepository.findAll().isEmpty()) {
            return null;
        } else {
            return empleadoRepository.findAll();
        }

    }

}
