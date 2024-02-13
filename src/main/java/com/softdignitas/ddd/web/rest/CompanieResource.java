package com.softdignitas.ddd.web.rest;

import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.repository.CompanieRepository;
import com.softdignitas.ddd.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.softdignitas.ddd.domain.Companie}.
 */
@RestController
@RequestMapping("/api/companies")
@Transactional
public class CompanieResource {

    private final Logger log = LoggerFactory.getLogger(CompanieResource.class);

    private static final String ENTITY_NAME = "companie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanieRepository companieRepository;

    public CompanieResource(CompanieRepository companieRepository) {
        this.companieRepository = companieRepository;
    }

    /**
     * {@code POST  /companies} : Create a new companie.
     *
     * @param companie the companie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companie, or with status {@code 400 (Bad Request)} if the companie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Companie> createCompanie(@RequestBody Companie companie) throws URISyntaxException {
        log.debug("REST request to save Companie : {}", companie);
        if (companie.getId() != null) {
            throw new BadRequestAlertException("A new companie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Companie result = companieRepository.save(companie);
        return ResponseEntity
            .created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /companies/:id} : Updates an existing companie.
     *
     * @param id the id of the companie to save.
     * @param companie the companie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companie,
     * or with status {@code 400 (Bad Request)} if the companie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Companie> updateCompanie(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Companie companie
    ) throws URISyntaxException {
        log.debug("REST request to update Companie : {}, {}", id, companie);
        if (companie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Companie result = companieRepository.save(companie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companie.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /companies/:id} : Partial updates given fields of an existing companie, field will ignore if it is null
     *
     * @param id the id of the companie to save.
     * @param companie the companie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companie,
     * or with status {@code 400 (Bad Request)} if the companie is not valid,
     * or with status {@code 404 (Not Found)} if the companie is not found,
     * or with status {@code 500 (Internal Server Error)} if the companie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Companie> partialUpdateCompanie(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Companie companie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Companie partially : {}, {}", id, companie);
        if (companie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Companie> result = companieRepository
            .findById(companie.getId())
            .map(existingCompanie -> {
                if (companie.getNume() != null) {
                    existingCompanie.setNume(companie.getNume());
                }

                return existingCompanie;
            })
            .map(companieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companie.getId())
        );
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("")
    public List<Companie> getAllCompanies() {
        log.debug("REST request to get all Companies");
        return companieRepository.findAll();
    }

    /**
     * {@code GET  /companies/:id} : get the "id" companie.
     *
     * @param id the id of the companie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Companie> getCompanie(@PathVariable("id") String id) {
        log.debug("REST request to get Companie : {}", id);
        Optional<Companie> companie = companieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(companie);
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" companie.
     *
     * @param id the id of the companie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanie(@PathVariable("id") String id) {
        log.debug("REST request to delete Companie : {}", id);
        companieRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
