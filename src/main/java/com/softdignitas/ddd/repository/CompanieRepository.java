package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Companie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Companie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanieRepository extends JpaRepository<Companie, Long> {}
