package com.softdignitas.ddd.web.rest;

import com.softdignitas.ddd.domain.CategorieClient;
import com.softdignitas.ddd.repository.CategorieClientRepository;
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
 * REST controller for managing {@link com.softdignitas.ddd.domain.CategorieClient}.
 */
@RestController
@RequestMapping("/api/categorie-clients")
@Transactional
public class CategorieClientResource {

    private final Logger log = LoggerFactory.getLogger(CategorieClientResource.class);

    private static final String ENTITY_NAME = "categorieClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieClientRepository categorieClientRepository;

    public CategorieClientResource(CategorieClientRepository categorieClientRepository) {
        this.categorieClientRepository = categorieClientRepository;
    }

    /**
     * {@code POST  /categorie-clients} : Create a new categorieClient.
     *
     * @param categorieClient the categorieClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieClient, or with status {@code 400 (Bad Request)} if the categorieClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CategorieClient> createCategorieClient(@RequestBody CategorieClient categorieClient) throws URISyntaxException {
        log.debug("REST request to save CategorieClient : {}", categorieClient);
        if (categorieClient.getId() != null) {
            throw new BadRequestAlertException("A new categorieClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieClient result = categorieClientRepository.save(categorieClient);
        return ResponseEntity
            .created(new URI("/api/categorie-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-clients/:id} : Updates an existing categorieClient.
     *
     * @param id the id of the categorieClient to save.
     * @param categorieClient the categorieClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieClient,
     * or with status {@code 400 (Bad Request)} if the categorieClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategorieClient> updateCategorieClient(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CategorieClient categorieClient
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieClient : {}, {}", id, categorieClient);
        if (categorieClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieClient result = categorieClientRepository.save(categorieClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieClient.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-clients/:id} : Partial updates given fields of an existing categorieClient, field will ignore if it is null
     *
     * @param id the id of the categorieClient to save.
     * @param categorieClient the categorieClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieClient,
     * or with status {@code 400 (Bad Request)} if the categorieClient is not valid,
     * or with status {@code 404 (Not Found)} if the categorieClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategorieClient> partialUpdateCategorieClient(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CategorieClient categorieClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieClient partially : {}, {}", id, categorieClient);
        if (categorieClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieClient> result = categorieClientRepository
            .findById(categorieClient.getId())
            .map(existingCategorieClient -> {
                if (categorieClient.getNume() != null) {
                    existingCategorieClient.setNume(categorieClient.getNume());
                }

                return existingCategorieClient;
            })
            .map(categorieClientRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorieClient.getId())
        );
    }

    /**
     * {@code GET  /categorie-clients} : get all the categorieClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieClients in body.
     */
    @GetMapping("")
    public List<CategorieClient> getAllCategorieClients() {
        log.debug("REST request to get all CategorieClients");
        return categorieClientRepository.findAll();
    }

    /**
     * {@code GET  /categorie-clients/:id} : get the "id" categorieClient.
     *
     * @param id the id of the categorieClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategorieClient> getCategorieClient(@PathVariable("id") String id) {
        log.debug("REST request to get CategorieClient : {}", id);
        Optional<CategorieClient> categorieClient = categorieClientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categorieClient);
    }

    /**
     * {@code DELETE  /categorie-clients/:id} : delete the "id" categorieClient.
     *
     * @param id the id of the categorieClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorieClient(@PathVariable("id") String id) {
        log.debug("REST request to delete CategorieClient : {}", id);
        categorieClientRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
