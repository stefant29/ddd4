package com.softdignitas.ddd.web.rest;

import com.softdignitas.ddd.domain.JTMaterialProcesVerbal;
import com.softdignitas.ddd.repository.JTMaterialProcesVerbalRepository;
import com.softdignitas.ddd.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.softdignitas.ddd.domain.JTMaterialProcesVerbal}.
 */
@RestController
@RequestMapping("/api/jt-material-proces-verbals")
@Transactional
public class JTMaterialProcesVerbalResource {

    private final Logger log = LoggerFactory.getLogger(JTMaterialProcesVerbalResource.class);

    private static final String ENTITY_NAME = "jTMaterialProcesVerbal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JTMaterialProcesVerbalRepository jTMaterialProcesVerbalRepository;

    public JTMaterialProcesVerbalResource(JTMaterialProcesVerbalRepository jTMaterialProcesVerbalRepository) {
        this.jTMaterialProcesVerbalRepository = jTMaterialProcesVerbalRepository;
    }

    /**
     * {@code POST  /jt-material-proces-verbals} : Create a new jTMaterialProcesVerbal.
     *
     * @param jTMaterialProcesVerbal the jTMaterialProcesVerbal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jTMaterialProcesVerbal, or with status {@code 400 (Bad Request)} if the jTMaterialProcesVerbal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JTMaterialProcesVerbal> createJTMaterialProcesVerbal(
        @Valid @RequestBody JTMaterialProcesVerbal jTMaterialProcesVerbal
    ) throws URISyntaxException {
        log.debug("REST request to save JTMaterialProcesVerbal : {}", jTMaterialProcesVerbal);
        if (jTMaterialProcesVerbal.getId() != null) {
            throw new BadRequestAlertException("A new jTMaterialProcesVerbal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JTMaterialProcesVerbal result = jTMaterialProcesVerbalRepository.save(jTMaterialProcesVerbal);
        return ResponseEntity
            .created(new URI("/api/jt-material-proces-verbals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /jt-material-proces-verbals/:id} : Updates an existing jTMaterialProcesVerbal.
     *
     * @param id the id of the jTMaterialProcesVerbal to save.
     * @param jTMaterialProcesVerbal the jTMaterialProcesVerbal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jTMaterialProcesVerbal,
     * or with status {@code 400 (Bad Request)} if the jTMaterialProcesVerbal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jTMaterialProcesVerbal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JTMaterialProcesVerbal> updateJTMaterialProcesVerbal(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody JTMaterialProcesVerbal jTMaterialProcesVerbal
    ) throws URISyntaxException {
        log.debug("REST request to update JTMaterialProcesVerbal : {}, {}", id, jTMaterialProcesVerbal);
        if (jTMaterialProcesVerbal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jTMaterialProcesVerbal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jTMaterialProcesVerbalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JTMaterialProcesVerbal result = jTMaterialProcesVerbalRepository.save(jTMaterialProcesVerbal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jTMaterialProcesVerbal.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /jt-material-proces-verbals/:id} : Partial updates given fields of an existing jTMaterialProcesVerbal, field will ignore if it is null
     *
     * @param id the id of the jTMaterialProcesVerbal to save.
     * @param jTMaterialProcesVerbal the jTMaterialProcesVerbal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jTMaterialProcesVerbal,
     * or with status {@code 400 (Bad Request)} if the jTMaterialProcesVerbal is not valid,
     * or with status {@code 404 (Not Found)} if the jTMaterialProcesVerbal is not found,
     * or with status {@code 500 (Internal Server Error)} if the jTMaterialProcesVerbal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JTMaterialProcesVerbal> partialUpdateJTMaterialProcesVerbal(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody JTMaterialProcesVerbal jTMaterialProcesVerbal
    ) throws URISyntaxException {
        log.debug("REST request to partial update JTMaterialProcesVerbal partially : {}, {}", id, jTMaterialProcesVerbal);
        if (jTMaterialProcesVerbal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jTMaterialProcesVerbal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jTMaterialProcesVerbalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JTMaterialProcesVerbal> result = jTMaterialProcesVerbalRepository
            .findById(jTMaterialProcesVerbal.getId())
            .map(existingJTMaterialProcesVerbal -> {
                if (jTMaterialProcesVerbal.getProcedura() != null) {
                    existingJTMaterialProcesVerbal.setProcedura(jTMaterialProcesVerbal.getProcedura());
                }
                if (jTMaterialProcesVerbal.getCantitate() != null) {
                    existingJTMaterialProcesVerbal.setCantitate(jTMaterialProcesVerbal.getCantitate());
                }

                return existingJTMaterialProcesVerbal;
            })
            .map(jTMaterialProcesVerbalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jTMaterialProcesVerbal.getId())
        );
    }

    /**
     * {@code GET  /jt-material-proces-verbals} : get all the jTMaterialProcesVerbals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jTMaterialProcesVerbals in body.
     */
    @GetMapping("")
    public List<JTMaterialProcesVerbal> getAllJTMaterialProcesVerbals() {
        log.debug("REST request to get all JTMaterialProcesVerbals");
        return jTMaterialProcesVerbalRepository.findAll();
    }

    /**
     * {@code GET  /jt-material-proces-verbals/:id} : get the "id" jTMaterialProcesVerbal.
     *
     * @param id the id of the jTMaterialProcesVerbal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jTMaterialProcesVerbal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JTMaterialProcesVerbal> getJTMaterialProcesVerbal(@PathVariable("id") String id) {
        log.debug("REST request to get JTMaterialProcesVerbal : {}", id);
        Optional<JTMaterialProcesVerbal> jTMaterialProcesVerbal = jTMaterialProcesVerbalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jTMaterialProcesVerbal);
    }

    /**
     * {@code DELETE  /jt-material-proces-verbals/:id} : delete the "id" jTMaterialProcesVerbal.
     *
     * @param id the id of the jTMaterialProcesVerbal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJTMaterialProcesVerbal(@PathVariable("id") String id) {
        log.debug("REST request to delete JTMaterialProcesVerbal : {}", id);
        jTMaterialProcesVerbalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
