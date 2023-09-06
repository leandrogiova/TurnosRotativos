package com.neoris.turnosrotativos.services.Implementacion;

import java.util.List;
import java.util.Optional;

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
        empleado.setearfechaDeCreacion();
        empleadoRepository.save(empleado);
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

}
