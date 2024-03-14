package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Client;
import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.service.dto.ClientIdAndDenumire;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends DDDRepository<Client, String> {
    List<ClientIdAndDenumire> findDistinctAllByCompanie(Companie companie);
}
