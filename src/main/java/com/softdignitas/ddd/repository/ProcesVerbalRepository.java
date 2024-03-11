package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.ProcesVerbal;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProcesVerbal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcesVerbalRepository extends DDDRepository<ProcesVerbal, String> {}
