package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.domain.Material;
import com.softdignitas.ddd.domain.enumeration.Procedura;
import com.softdignitas.ddd.service.dto.MaterialIdDenumire;
import com.softdignitas.ddd.service.dto.UtilizatorIdNumePrenume;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Material entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialRepository extends DDDRepository<Material, String> {
    List<MaterialIdDenumire> findDistinctAllByCompanieAndProcedura(Companie companie, Procedura procedura);
}
