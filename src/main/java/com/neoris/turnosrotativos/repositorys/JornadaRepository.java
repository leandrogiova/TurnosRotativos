package com.neoris.turnosrotativos.repositorys;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoris.turnosrotativos.entities.Jornada;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {

    List<Jornada> findByNroDocumentoAndFecha(Long nroDocumento, LocalDate fecha);

    List<Jornada> findByNroDocumentoAndFecha_(Long nroDocumento, LocalDate fecha);

    List<Jornada> findByNroDocumento(Long nroDocumento);

    List<Jornada> findByFecha(LocalDate fecha);

    boolean existsByNroDocumentoAndFechaAndIdConcepto(Long nroDocumento, LocalDate fecha, Integer idConcepto);

    Integer countHorasTrabajadasByNroDocumentoAndFecha(Long nroDocumento, LocalDate fecha);

    List<Jornada> findByNroDocumentoAndFechaBetween(Long nroDocumento, LocalDate fechaInicioSemana,
            LocalDate fechaFinSemana);
}
