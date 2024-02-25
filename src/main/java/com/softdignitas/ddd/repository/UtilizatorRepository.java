package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.User;
import com.softdignitas.ddd.domain.Utilizator;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Utilizator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtilizatorRepository extends DDDRepository<Utilizator, String> {
    Optional<Utilizator> findOneByUser(User user);
}
