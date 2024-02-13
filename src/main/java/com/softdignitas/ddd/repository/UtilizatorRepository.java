package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Utilizator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Utilizator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtilizatorRepository extends JpaRepository<Utilizator, String> {}
