package com.softdignitas.ddd.web.rest;

import com.softdignitas.ddd.domain.User;
import com.softdignitas.ddd.domain.Utilizator;
import com.softdignitas.ddd.repository.UserRepository;
import com.softdignitas.ddd.repository.UtilizatorRepository;
import com.softdignitas.ddd.security.SecurityUtils;
import com.softdignitas.ddd.service.dto.UtilizatorDTO;
import com.softdignitas.ddd.service.mapper.UserMapper;
import com.softdignitas.ddd.web.rest.errors.BadRequestAlertException;
import com.softdignitas.ddd.web.rest.errors.RecordNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link com.softdignitas.ddd.domain.Utilizator}.
 */
@RestController
@RequestMapping("/api/utilizators")
@Transactional
public class UtilizatorResource extends DDDEntitateResource<Utilizator> {

    private final Logger log = LoggerFactory.getLogger(UtilizatorResource.class);

    private static final String ENTITY_NAME = "utilizator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UtilizatorRepository utilizatorRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UtilizatorResource(UtilizatorRepository utilizatorRepository, UserRepository userRepository, UserMapper userMapper) {
        this.utilizatorRepository = utilizatorRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * {@code POST  /utilizators} : Create a new utilizator.
     *
     * @param utilizatorDTO the utilizator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new utilizator, or with status {@code 400 (Bad Request)} if the utilizator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Utilizator> createUtilizator(@Valid @RequestBody UtilizatorDTO utilizatorDTO) throws URISyntaxException {
        log.debug("REST request to save Utilizator : {}", utilizatorDTO);
        if (utilizatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new utilizator cannot already have an ID", ENTITY_NAME, "idexists");
        }

        /* create user  */
        User user = userMapper.createNewUserFromUtilizatorDto(utilizatorDTO);
        User savedUser = userRepository.save(user);

        utilizatorDTO.setUser(savedUser);

        Utilizator utilizator = userMapper.fromUtilizatorDto(utilizatorDTO);
        // get current company

        utilizator.companie(
            SecurityUtils
                .getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .flatMap(utilizatorRepository::findOneByUser)
                .orElseThrow(() -> new RecordNotFoundException("UTILIZATOR", ""))
                .getCompanie()
        );

        Utilizator result = utilizatorRepository.save(utilizator);
        return ResponseEntity
            .created(new URI("/api/utilizators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /utilizators/:id} : Updates an existing utilizator.
     *
     * @param id the id of the utilizator to save.
     * @param utilizator the utilizator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated utilizator,
     * or with status {@code 400 (Bad Request)} if the utilizator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the utilizator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Utilizator> updateUtilizator(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Utilizator utilizator
    ) throws URISyntaxException {
        log.debug("REST request to update Utilizator : {}, {}", id, utilizator);
        if (utilizator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, utilizator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!utilizatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Utilizator result = utilizatorRepository.save(utilizator);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, utilizator.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /utilizators/:id} : Partial updates given fields of an existing utilizator, field will ignore if it is null
     *
     * @param id the id of the utilizator to save.
     * @param utilizator the utilizator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated utilizator,
     * or with status {@code 400 (Bad Request)} if the utilizator is not valid,
     * or with status {@code 404 (Not Found)} if the utilizator is not found,
     * or with status {@code 500 (Internal Server Error)} if the utilizator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Utilizator> partialUpdateUtilizator(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Utilizator utilizator
    ) throws URISyntaxException {
        log.debug("REST request to partial update Utilizator partially : {}, {}", id, utilizator);
        if (utilizator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, utilizator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!utilizatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Utilizator> result = utilizatorRepository
            .findById(utilizator.getId())
            .map(existingUtilizator -> {
                if (utilizator.getNume() != null) {
                    existingUtilizator.setNume(utilizator.getNume());
                }
                if (utilizator.getPrenume() != null) {
                    existingUtilizator.setPrenume(utilizator.getPrenume());
                }
                if (utilizator.getFunctie() != null) {
                    existingUtilizator.setFunctie(utilizator.getFunctie());
                }

                return existingUtilizator;
            })
            .map(utilizatorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, utilizator.getId())
        );
    }

    /**
     * {@code GET  /utilizators/:id} : get the "id" utilizator.
     *
     * @param id the id of the utilizator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the utilizator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizator(@PathVariable("id") String id) {
        log.debug("REST request to get Utilizator : {}", id);
        Optional<Utilizator> utilizator = utilizatorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(utilizator);
    }

    /**
     * {@code DELETE  /utilizators/:id} : delete the "id" utilizator.
     *
     * @param id the id of the utilizator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilizator(@PathVariable("id") String id) {
        log.debug("REST request to delete Utilizator : {}", id);
        utilizatorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
