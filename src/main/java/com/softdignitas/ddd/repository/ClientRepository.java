package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Client;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends DDDRepository<Client, String> {}
