package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.domain.ProcesVerbal;
import com.softdignitas.ddd.service.dto.ProcesVerbalDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProcesVerbal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcesVerbalRepository extends DDDRepository<ProcesVerbal, String> {
    List<ProcesVerbal> findAllByCompanie(Companie companie);
}
