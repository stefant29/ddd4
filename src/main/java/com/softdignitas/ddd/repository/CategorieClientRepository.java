package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.CategorieClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategorieClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieClientRepository extends DDDRepository<CategorieClient, String> {}
