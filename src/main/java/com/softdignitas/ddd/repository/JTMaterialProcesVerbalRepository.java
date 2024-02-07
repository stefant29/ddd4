package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.JTMaterialProcesVerbal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JTMaterialProcesVerbal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JTMaterialProcesVerbalRepository extends JpaRepository<JTMaterialProcesVerbal, String> {}
