package com.neoris.turnosrotativos.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoris.turnosrotativos.entities.Jornada;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {

}
