package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Material;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Material entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialRepository extends DDDRepository<Material, String> {}
