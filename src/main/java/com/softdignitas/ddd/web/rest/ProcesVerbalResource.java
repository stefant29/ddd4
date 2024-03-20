package com.softdignitas.ddd.web.rest;

import com.softdignitas.ddd.domain.JTMaterialProcesVerbal;
import com.softdignitas.ddd.domain.ProcesVerbal;
import com.softdignitas.ddd.repository.JTMaterialProcesVerbalRepository;
import com.softdignitas.ddd.repository.ProcesVerbalRepository;
import com.softdignitas.ddd.service.dto.ProcesVerbalDTO;
import com.softdignitas.ddd.service.mapper.ProcesVerbalMapper;
import com.softdignitas.ddd.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
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
 * REST controller for managing {@link com.softdignitas.ddd.domain.ProcesVerbal}.
 */
@RestController
@RequestMapping("/api/proces-verbals")
@Transactional
public class ProcesVerbalResource extends DDDEntitateResource<ProcesVerbal> {

    private final Logger log = LoggerFactory.getLogger(ProcesVerbalResource.class);

    private static final String ENTITY_NAME = "procesVerbal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcesVerbalRepository procesVerbalRepository;
    private final JTMaterialProcesVerbalRepository jtMaterialProcesVerbalRepository;

    public ProcesVerbalResource(
        ProcesVerbalRepository procesVerbalRepository,
        JTMaterialProcesVerbalRepository jtMaterialProcesVerbalRepository
    ) {
        this.procesVerbalRepository = procesVerbalRepository;
        this.jtMaterialProcesVerbalRepository = jtMaterialProcesVerbalRepository;

        super.repository = procesVerbalRepository;
    }

    /**
     * {@code POST  /proces-verbals} : Create a new procesVerbal.
     *
     * @param procesVerbalDTO the procesVerbal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procesVerbal, or with status {@code 400 (Bad Request)} if the procesVerbal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProcesVerbal> createProcesVerbal(@Valid @RequestBody ProcesVerbalDTO procesVerbalDTO) throws URISyntaxException {
        log.debug("REST request to save ProcesVerbal : {}", procesVerbalDTO);

        ProcesVerbal procesVerbal = ProcesVerbalMapper.toEntity(procesVerbalDTO);

        final var companie = getCompanie();
        procesVerbal.setCompanie(companie);
        procesVerbal.setData(procesVerbalDTO.ora().atZone(ZoneId.systemDefault()).toLocalDate());

        ProcesVerbal result = procesVerbalRepository.save(procesVerbal);

        procesVerbalDTO
            .jTMaterialProcesVerbals()
            .forEach(jTMaterialProcesVerbal -> {
                jTMaterialProcesVerbal.setProcesVerbal(result);
                jtMaterialProcesVerbalRepository.save(jTMaterialProcesVerbal);
            });

        return ResponseEntity
            .created(new URI("/api/proces-verbals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /proces-verbals/:id} : Updates an existing procesVerbal.
     *
     * @param id the id of the procesVerbal to save.
     * @param procesVerbalDTO the procesVerbal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesVerbal,
     * or with status {@code 400 (Bad Request)} if the procesVerbal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procesVerbal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProcesVerbal> updateProcesVerbal(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ProcesVerbalDTO procesVerbalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcesVerbal : {}, {}", id, procesVerbalDTO);
        if (procesVerbalDTO.id() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesVerbalDTO.id())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesVerbalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcesVerbal procesVerbal = ProcesVerbalMapper.toEntity(procesVerbalDTO);

        final var companie = getCompanie();
        procesVerbal.setCompanie(companie);
        procesVerbal.setData(procesVerbalDTO.ora().atZone(ZoneId.systemDefault()).toLocalDate());

        ProcesVerbal result = procesVerbalRepository.save(procesVerbal);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procesVerbal.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /proces-verbals/:id} : Partial updates given fields of an existing procesVerbal, field will ignore if it is null
     *
     * @param id the id of the procesVerbal to save.
     * @param procesVerbal the procesVerbal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesVerbal,
     * or with status {@code 400 (Bad Request)} if the procesVerbal is not valid,
     * or with status {@code 404 (Not Found)} if the procesVerbal is not found,
     * or with status {@code 500 (Internal Server Error)} if the procesVerbal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcesVerbal> partialUpdateProcesVerbal(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ProcesVerbal procesVerbal
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcesVerbal partially : {}, {}", id, procesVerbal);
        if (procesVerbal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesVerbal.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesVerbalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcesVerbal> result = procesVerbalRepository
            .findById(procesVerbal.getId())
            .map(existingProcesVerbal -> {
                if (procesVerbal.getData() != null) {
                    existingProcesVerbal.setData(procesVerbal.getData());
                }
                if (procesVerbal.getOra() != null) {
                    existingProcesVerbal.setOra(procesVerbal.getOra());
                }
                if (procesVerbal.getNumarProcesVerbal() != null) {
                    existingProcesVerbal.setNumarProcesVerbal(procesVerbal.getNumarProcesVerbal());
                }
                if (procesVerbal.getReprezentant() != null) {
                    existingProcesVerbal.setReprezentant(procesVerbal.getReprezentant());
                }
                if (procesVerbal.getSpatii() != null) {
                    existingProcesVerbal.setSpatii(procesVerbal.getSpatii());
                }
                if (procesVerbal.getSuprafata() != null) {
                    existingProcesVerbal.setSuprafata(procesVerbal.getSuprafata());
                }
                if (procesVerbal.getRapelDezinsectie() != null) {
                    existingProcesVerbal.setRapelDezinsectie(procesVerbal.getRapelDezinsectie());
                }
                if (procesVerbal.getRapelDeratizare() != null) {
                    existingProcesVerbal.setRapelDeratizare(procesVerbal.getRapelDeratizare());
                }
                if (procesVerbal.getGarantieDezinsectie() != null) {
                    existingProcesVerbal.setGarantieDezinsectie(procesVerbal.getGarantieDezinsectie());
                }
                if (procesVerbal.getGarantieDeratizare() != null) {
                    existingProcesVerbal.setGarantieDeratizare(procesVerbal.getGarantieDeratizare());
                }

                return existingProcesVerbal;
            })
            .map(procesVerbalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procesVerbal.getId())
        );
    }

    /**
     * {@code GET  /proces-verbals/:id} : get the "id" procesVerbal.
     *
     * @param id the id of the procesVerbal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procesVerbal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProcesVerbal> getProcesVerbal(@PathVariable("id") String id) {
        log.debug("REST request to get ProcesVerbal : {}", id);
        Optional<ProcesVerbal> procesVerbal = procesVerbalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(procesVerbal);
    }

    /**
     * {@code DELETE  /proces-verbals/:id} : delete the "id" procesVerbal.
     *
     * @param id the id of the procesVerbal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcesVerbal(@PathVariable("id") String id) {
        log.debug("REST request to delete ProcesVerbal : {}", id);
        procesVerbalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
