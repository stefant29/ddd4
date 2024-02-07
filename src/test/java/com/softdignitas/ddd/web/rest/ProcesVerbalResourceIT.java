package com.softdignitas.ddd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.softdignitas.ddd.IntegrationTest;
import com.softdignitas.ddd.domain.Client;
import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.domain.ProcesVerbal;
import com.softdignitas.ddd.domain.Utilizator;
import com.softdignitas.ddd.repository.ProcesVerbalRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProcesVerbalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcesVerbalResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_ORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NUMAR_PROCES_VERBAL = 1;
    private static final Integer UPDATED_NUMAR_PROCES_VERBAL = 2;

    private static final String DEFAULT_REPREZENTANT = "AAAAAAAAAA";
    private static final String UPDATED_REPREZENTANT = "BBBBBBBBBB";

    private static final String DEFAULT_SPATII = "AAAAAAAAAA";
    private static final String UPDATED_SPATII = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUPRAFATA = 1;
    private static final Integer UPDATED_SUPRAFATA = 2;

    private static final Integer DEFAULT_RAPEL_DEZINSECTIE = 1;
    private static final Integer UPDATED_RAPEL_DEZINSECTIE = 2;

    private static final Integer DEFAULT_RAPEL_DERATIZARE = 1;
    private static final Integer UPDATED_RAPEL_DERATIZARE = 2;

    private static final Boolean DEFAULT_GARANTIE_DEZINSECTIE = false;
    private static final Boolean UPDATED_GARANTIE_DEZINSECTIE = true;

    private static final Boolean DEFAULT_GARANTIE_DERATIZARE = false;
    private static final Boolean UPDATED_GARANTIE_DERATIZARE = true;

    private static final String ENTITY_API_URL = "/api/proces-verbals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProcesVerbalRepository procesVerbalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcesVerbalMockMvc;

    private ProcesVerbal procesVerbal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcesVerbal createEntity(EntityManager em) {
        ProcesVerbal procesVerbal = new ProcesVerbal()
            .data(DEFAULT_DATA)
            .ora(DEFAULT_ORA)
            .numarProcesVerbal(DEFAULT_NUMAR_PROCES_VERBAL)
            .reprezentant(DEFAULT_REPREZENTANT)
            .spatii(DEFAULT_SPATII)
            .suprafata(DEFAULT_SUPRAFATA)
            .rapelDezinsectie(DEFAULT_RAPEL_DEZINSECTIE)
            .rapelDeratizare(DEFAULT_RAPEL_DERATIZARE)
            .garantieDezinsectie(DEFAULT_GARANTIE_DEZINSECTIE)
            .garantieDeratizare(DEFAULT_GARANTIE_DERATIZARE);
        // Add required entity
        Companie companie;
        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
            companie = CompanieResourceIT.createEntity(em);
            em.persist(companie);
            em.flush();
        } else {
            companie = TestUtil.findAll(em, Companie.class).get(0);
        }
        procesVerbal.setCompanie(companie);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        procesVerbal.setClient(client);
        // Add required entity
        Utilizator utilizator;
        if (TestUtil.findAll(em, Utilizator.class).isEmpty()) {
            utilizator = UtilizatorResourceIT.createEntity(em);
            em.persist(utilizator);
            em.flush();
        } else {
            utilizator = TestUtil.findAll(em, Utilizator.class).get(0);
        }
        procesVerbal.setOperator(utilizator);
        return procesVerbal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcesVerbal createUpdatedEntity(EntityManager em) {
        ProcesVerbal procesVerbal = new ProcesVerbal()
            .data(UPDATED_DATA)
            .ora(UPDATED_ORA)
            .numarProcesVerbal(UPDATED_NUMAR_PROCES_VERBAL)
            .reprezentant(UPDATED_REPREZENTANT)
            .spatii(UPDATED_SPATII)
            .suprafata(UPDATED_SUPRAFATA)
            .rapelDezinsectie(UPDATED_RAPEL_DEZINSECTIE)
            .rapelDeratizare(UPDATED_RAPEL_DERATIZARE)
            .garantieDezinsectie(UPDATED_GARANTIE_DEZINSECTIE)
            .garantieDeratizare(UPDATED_GARANTIE_DERATIZARE);
        // Add required entity
        Companie companie;
        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
            companie = CompanieResourceIT.createUpdatedEntity(em);
            em.persist(companie);
            em.flush();
        } else {
            companie = TestUtil.findAll(em, Companie.class).get(0);
        }
        procesVerbal.setCompanie(companie);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        procesVerbal.setClient(client);
        // Add required entity
        Utilizator utilizator;
        if (TestUtil.findAll(em, Utilizator.class).isEmpty()) {
            utilizator = UtilizatorResourceIT.createUpdatedEntity(em);
            em.persist(utilizator);
            em.flush();
        } else {
            utilizator = TestUtil.findAll(em, Utilizator.class).get(0);
        }
        procesVerbal.setOperator(utilizator);
        return procesVerbal;
    }

    @BeforeEach
    public void initTest() {
        procesVerbal = createEntity(em);
    }

    @Test
    @Transactional
    void createProcesVerbal() throws Exception {
        int databaseSizeBeforeCreate = procesVerbalRepository.findAll().size();
        // Create the ProcesVerbal
        restProcesVerbalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesVerbal)))
            .andExpect(status().isCreated());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeCreate + 1);
        ProcesVerbal testProcesVerbal = procesVerbalList.get(procesVerbalList.size() - 1);
        assertThat(testProcesVerbal.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testProcesVerbal.getOra()).isEqualTo(DEFAULT_ORA);
        assertThat(testProcesVerbal.getNumarProcesVerbal()).isEqualTo(DEFAULT_NUMAR_PROCES_VERBAL);
        assertThat(testProcesVerbal.getReprezentant()).isEqualTo(DEFAULT_REPREZENTANT);
        assertThat(testProcesVerbal.getSpatii()).isEqualTo(DEFAULT_SPATII);
        assertThat(testProcesVerbal.getSuprafata()).isEqualTo(DEFAULT_SUPRAFATA);
        assertThat(testProcesVerbal.getRapelDezinsectie()).isEqualTo(DEFAULT_RAPEL_DEZINSECTIE);
        assertThat(testProcesVerbal.getRapelDeratizare()).isEqualTo(DEFAULT_RAPEL_DERATIZARE);
        assertThat(testProcesVerbal.getGarantieDezinsectie()).isEqualTo(DEFAULT_GARANTIE_DEZINSECTIE);
        assertThat(testProcesVerbal.getGarantieDeratizare()).isEqualTo(DEFAULT_GARANTIE_DERATIZARE);
    }

    @Test
    @Transactional
    void createProcesVerbalWithExistingId() throws Exception {
        // Create the ProcesVerbal with an existing ID
        procesVerbal.setId("existing_id");

        int databaseSizeBeforeCreate = procesVerbalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcesVerbalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesVerbal)))
            .andExpect(status().isBadRequest());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesVerbalRepository.findAll().size();
        // set the field null
        procesVerbal.setData(null);

        // Create the ProcesVerbal, which fails.

        restProcesVerbalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesVerbal)))
            .andExpect(status().isBadRequest());

        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumarProcesVerbalIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesVerbalRepository.findAll().size();
        // set the field null
        procesVerbal.setNumarProcesVerbal(null);

        // Create the ProcesVerbal, which fails.

        restProcesVerbalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesVerbal)))
            .andExpect(status().isBadRequest());

        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProcesVerbals() throws Exception {
        // Initialize the database
        procesVerbalRepository.saveAndFlush(procesVerbal);

        // Get all the procesVerbalList
        restProcesVerbalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procesVerbal.getId())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].ora").value(hasItem(DEFAULT_ORA.toString())))
            .andExpect(jsonPath("$.[*].numarProcesVerbal").value(hasItem(DEFAULT_NUMAR_PROCES_VERBAL)))
            .andExpect(jsonPath("$.[*].reprezentant").value(hasItem(DEFAULT_REPREZENTANT)))
            .andExpect(jsonPath("$.[*].spatii").value(hasItem(DEFAULT_SPATII)))
            .andExpect(jsonPath("$.[*].suprafata").value(hasItem(DEFAULT_SUPRAFATA)))
            .andExpect(jsonPath("$.[*].rapelDezinsectie").value(hasItem(DEFAULT_RAPEL_DEZINSECTIE)))
            .andExpect(jsonPath("$.[*].rapelDeratizare").value(hasItem(DEFAULT_RAPEL_DERATIZARE)))
            .andExpect(jsonPath("$.[*].garantieDezinsectie").value(hasItem(DEFAULT_GARANTIE_DEZINSECTIE.booleanValue())))
            .andExpect(jsonPath("$.[*].garantieDeratizare").value(hasItem(DEFAULT_GARANTIE_DERATIZARE.booleanValue())));
    }

    @Test
    @Transactional
    void getProcesVerbal() throws Exception {
        // Initialize the database
        procesVerbalRepository.saveAndFlush(procesVerbal);

        // Get the procesVerbal
        restProcesVerbalMockMvc
            .perform(get(ENTITY_API_URL_ID, procesVerbal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procesVerbal.getId()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.ora").value(DEFAULT_ORA.toString()))
            .andExpect(jsonPath("$.numarProcesVerbal").value(DEFAULT_NUMAR_PROCES_VERBAL))
            .andExpect(jsonPath("$.reprezentant").value(DEFAULT_REPREZENTANT))
            .andExpect(jsonPath("$.spatii").value(DEFAULT_SPATII))
            .andExpect(jsonPath("$.suprafata").value(DEFAULT_SUPRAFATA))
            .andExpect(jsonPath("$.rapelDezinsectie").value(DEFAULT_RAPEL_DEZINSECTIE))
            .andExpect(jsonPath("$.rapelDeratizare").value(DEFAULT_RAPEL_DERATIZARE))
            .andExpect(jsonPath("$.garantieDezinsectie").value(DEFAULT_GARANTIE_DEZINSECTIE.booleanValue()))
            .andExpect(jsonPath("$.garantieDeratizare").value(DEFAULT_GARANTIE_DERATIZARE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProcesVerbal() throws Exception {
        // Get the procesVerbal
        restProcesVerbalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcesVerbal() throws Exception {
        // Initialize the database
        procesVerbalRepository.saveAndFlush(procesVerbal);

        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();

        // Update the procesVerbal
        ProcesVerbal updatedProcesVerbal = procesVerbalRepository.findById(procesVerbal.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProcesVerbal are not directly saved in db
        em.detach(updatedProcesVerbal);
        updatedProcesVerbal
            .data(UPDATED_DATA)
            .ora(UPDATED_ORA)
            .numarProcesVerbal(UPDATED_NUMAR_PROCES_VERBAL)
            .reprezentant(UPDATED_REPREZENTANT)
            .spatii(UPDATED_SPATII)
            .suprafata(UPDATED_SUPRAFATA)
            .rapelDezinsectie(UPDATED_RAPEL_DEZINSECTIE)
            .rapelDeratizare(UPDATED_RAPEL_DERATIZARE)
            .garantieDezinsectie(UPDATED_GARANTIE_DEZINSECTIE)
            .garantieDeratizare(UPDATED_GARANTIE_DERATIZARE);

        restProcesVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProcesVerbal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProcesVerbal))
            )
            .andExpect(status().isOk());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
        ProcesVerbal testProcesVerbal = procesVerbalList.get(procesVerbalList.size() - 1);
        assertThat(testProcesVerbal.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testProcesVerbal.getOra()).isEqualTo(UPDATED_ORA);
        assertThat(testProcesVerbal.getNumarProcesVerbal()).isEqualTo(UPDATED_NUMAR_PROCES_VERBAL);
        assertThat(testProcesVerbal.getReprezentant()).isEqualTo(UPDATED_REPREZENTANT);
        assertThat(testProcesVerbal.getSpatii()).isEqualTo(UPDATED_SPATII);
        assertThat(testProcesVerbal.getSuprafata()).isEqualTo(UPDATED_SUPRAFATA);
        assertThat(testProcesVerbal.getRapelDezinsectie()).isEqualTo(UPDATED_RAPEL_DEZINSECTIE);
        assertThat(testProcesVerbal.getRapelDeratizare()).isEqualTo(UPDATED_RAPEL_DERATIZARE);
        assertThat(testProcesVerbal.getGarantieDezinsectie()).isEqualTo(UPDATED_GARANTIE_DEZINSECTIE);
        assertThat(testProcesVerbal.getGarantieDeratizare()).isEqualTo(UPDATED_GARANTIE_DERATIZARE);
    }

    @Test
    @Transactional
    void putNonExistingProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();
        procesVerbal.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procesVerbal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();
        procesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();
        procesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesVerbalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesVerbal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcesVerbalWithPatch() throws Exception {
        // Initialize the database
        procesVerbalRepository.saveAndFlush(procesVerbal);

        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();

        // Update the procesVerbal using partial update
        ProcesVerbal partialUpdatedProcesVerbal = new ProcesVerbal();
        partialUpdatedProcesVerbal.setId(procesVerbal.getId());

        partialUpdatedProcesVerbal
            .ora(UPDATED_ORA)
            .numarProcesVerbal(UPDATED_NUMAR_PROCES_VERBAL)
            .spatii(UPDATED_SPATII)
            .garantieDezinsectie(UPDATED_GARANTIE_DEZINSECTIE)
            .garantieDeratizare(UPDATED_GARANTIE_DERATIZARE);

        restProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesVerbal))
            )
            .andExpect(status().isOk());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
        ProcesVerbal testProcesVerbal = procesVerbalList.get(procesVerbalList.size() - 1);
        assertThat(testProcesVerbal.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testProcesVerbal.getOra()).isEqualTo(UPDATED_ORA);
        assertThat(testProcesVerbal.getNumarProcesVerbal()).isEqualTo(UPDATED_NUMAR_PROCES_VERBAL);
        assertThat(testProcesVerbal.getReprezentant()).isEqualTo(DEFAULT_REPREZENTANT);
        assertThat(testProcesVerbal.getSpatii()).isEqualTo(UPDATED_SPATII);
        assertThat(testProcesVerbal.getSuprafata()).isEqualTo(DEFAULT_SUPRAFATA);
        assertThat(testProcesVerbal.getRapelDezinsectie()).isEqualTo(DEFAULT_RAPEL_DEZINSECTIE);
        assertThat(testProcesVerbal.getRapelDeratizare()).isEqualTo(DEFAULT_RAPEL_DERATIZARE);
        assertThat(testProcesVerbal.getGarantieDezinsectie()).isEqualTo(UPDATED_GARANTIE_DEZINSECTIE);
        assertThat(testProcesVerbal.getGarantieDeratizare()).isEqualTo(UPDATED_GARANTIE_DERATIZARE);
    }

    @Test
    @Transactional
    void fullUpdateProcesVerbalWithPatch() throws Exception {
        // Initialize the database
        procesVerbalRepository.saveAndFlush(procesVerbal);

        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();

        // Update the procesVerbal using partial update
        ProcesVerbal partialUpdatedProcesVerbal = new ProcesVerbal();
        partialUpdatedProcesVerbal.setId(procesVerbal.getId());

        partialUpdatedProcesVerbal
            .data(UPDATED_DATA)
            .ora(UPDATED_ORA)
            .numarProcesVerbal(UPDATED_NUMAR_PROCES_VERBAL)
            .reprezentant(UPDATED_REPREZENTANT)
            .spatii(UPDATED_SPATII)
            .suprafata(UPDATED_SUPRAFATA)
            .rapelDezinsectie(UPDATED_RAPEL_DEZINSECTIE)
            .rapelDeratizare(UPDATED_RAPEL_DERATIZARE)
            .garantieDezinsectie(UPDATED_GARANTIE_DEZINSECTIE)
            .garantieDeratizare(UPDATED_GARANTIE_DERATIZARE);

        restProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesVerbal))
            )
            .andExpect(status().isOk());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
        ProcesVerbal testProcesVerbal = procesVerbalList.get(procesVerbalList.size() - 1);
        assertThat(testProcesVerbal.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testProcesVerbal.getOra()).isEqualTo(UPDATED_ORA);
        assertThat(testProcesVerbal.getNumarProcesVerbal()).isEqualTo(UPDATED_NUMAR_PROCES_VERBAL);
        assertThat(testProcesVerbal.getReprezentant()).isEqualTo(UPDATED_REPREZENTANT);
        assertThat(testProcesVerbal.getSpatii()).isEqualTo(UPDATED_SPATII);
        assertThat(testProcesVerbal.getSuprafata()).isEqualTo(UPDATED_SUPRAFATA);
        assertThat(testProcesVerbal.getRapelDezinsectie()).isEqualTo(UPDATED_RAPEL_DEZINSECTIE);
        assertThat(testProcesVerbal.getRapelDeratizare()).isEqualTo(UPDATED_RAPEL_DERATIZARE);
        assertThat(testProcesVerbal.getGarantieDezinsectie()).isEqualTo(UPDATED_GARANTIE_DEZINSECTIE);
        assertThat(testProcesVerbal.getGarantieDeratizare()).isEqualTo(UPDATED_GARANTIE_DERATIZARE);
    }

    @Test
    @Transactional
    void patchNonExistingProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();
        procesVerbal.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, procesVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();
        procesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = procesVerbalRepository.findAll().size();
        procesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(procesVerbal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcesVerbal in the database
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcesVerbal() throws Exception {
        // Initialize the database
        procesVerbalRepository.saveAndFlush(procesVerbal);

        int databaseSizeBeforeDelete = procesVerbalRepository.findAll().size();

        // Delete the procesVerbal
        restProcesVerbalMockMvc
            .perform(delete(ENTITY_API_URL_ID, procesVerbal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcesVerbal> procesVerbalList = procesVerbalRepository.findAll();
        assertThat(procesVerbalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
