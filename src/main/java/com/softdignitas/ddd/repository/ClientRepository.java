package com.softdignitas.ddd.repository;

import com.softdignitas.ddd.domain.Client;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends DDDRepository<Client, String> {
    @Query("SELECT new Client(entity.id, entity.denumire) FROM Client entity")
    List<Client> findIdAndName();
}
