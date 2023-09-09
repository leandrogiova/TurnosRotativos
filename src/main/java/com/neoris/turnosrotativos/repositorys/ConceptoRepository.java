package com.neoris.turnosrotativos.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoris.turnosrotativos.entities.Concepto;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {

}
