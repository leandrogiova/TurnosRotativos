package com.neoris.turnosrotativos.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.neoris.turnosrotativos.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByNroDocumento(Long nroDocumento);

    Optional<Empleado> findByEmail(String email);

    boolean existsByNroDocumentoAndIdNot(Long nroDocumento, Long empleadoId);

    boolean existsByEmailAndIdNot(String email, Long empleadoId);
}
