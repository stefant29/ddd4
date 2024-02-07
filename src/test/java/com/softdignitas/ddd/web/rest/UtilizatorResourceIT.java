package com.softdignitas.ddd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.softdignitas.ddd.IntegrationTest;
import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.domain.Utilizator;
import com.softdignitas.ddd.domain.enumeration.Functie;
import com.softdignitas.ddd.repository.UtilizatorRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link UtilizatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UtilizatorResourceIT {

    private static final String DEFAULT_NUME = "AAAAAAAAAA";
    private static final String UPDATED_NUME = "BBBBBBBBBB";

    private static final String DEFAULT_PRENUME = "AAAAAAAAAA";
    private static final String UPDATED_PRENUME = "BBBBBBBBBB";

    private static final Functie DEFAULT_FUNCTIE = Functie.SUPERADMIN;
    private static final Functie UPDATED_FUNCTIE = Functie.ADMIN;

    private static final String ENTITY_API_URL = "/api/utilizators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUtilizatorMockMvc;

    private Utilizator utilizator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utilizator createEntity(EntityManager em) {
        Utilizator utilizator = new Utilizator().nume(DEFAULT_NUME).prenume(DEFAULT_PRENUME).functie(DEFAULT_FUNCTIE);
        // Add required entity
        Companie companie;
        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
            companie = CompanieResourceIT.createEntity(em);
            em.persist(companie);
            em.flush();
        } else {
            companie = TestUtil.findAll(em, Companie.class).get(0);
        }
        utilizator.setCompanie(companie);
        return utilizator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utilizator createUpdatedEntity(EntityManager em) {
        Utilizator utilizator = new Utilizator().nume(UPDATED_NUME).prenume(UPDATED_PRENUME).functie(UPDATED_FUNCTIE);
        // Add required entity
        Companie companie;
        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
            companie = CompanieResourceIT.createUpdatedEntity(em);
            em.persist(companie);
            em.flush();
        } else {
            companie = TestUtil.findAll(em, Companie.class).get(0);
        }
        utilizator.setCompanie(companie);
        return utilizator;
    }

    @BeforeEach
    public void initTest() {
        utilizator = createEntity(em);
    }

    @Test
    @Transactional
    void createUtilizator() throws Exception {
        int databaseSizeBeforeCreate = utilizatorRepository.findAll().size();
        // Create the Utilizator
        restUtilizatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilizator)))
            .andExpect(status().isCreated());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeCreate + 1);
        Utilizator testUtilizator = utilizatorList.get(utilizatorList.size() - 1);
        assertThat(testUtilizator.getNume()).isEqualTo(DEFAULT_NUME);
        assertThat(testUtilizator.getPrenume()).isEqualTo(DEFAULT_PRENUME);
        assertThat(testUtilizator.getFunctie()).isEqualTo(DEFAULT_FUNCTIE);
    }

    @Test
    @Transactional
    void createUtilizatorWithExistingId() throws Exception {
        // Create the Utilizator with an existing ID
        utilizator.setId("existing_id");

        int databaseSizeBeforeCreate = utilizatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtilizatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilizator)))
            .andExpect(status().isBadRequest());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizatorRepository.findAll().size();
        // set the field null
        utilizator.setNume(null);

        // Create the Utilizator, which fails.

        restUtilizatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilizator)))
            .andExpect(status().isBadRequest());

        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizatorRepository.findAll().size();
        // set the field null
        utilizator.setPrenume(null);

        // Create the Utilizator, which fails.

        restUtilizatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilizator)))
            .andExpect(status().isBadRequest());

        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFunctieIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilizatorRepository.findAll().size();
        // set the field null
        utilizator.setFunctie(null);

        // Create the Utilizator, which fails.

        restUtilizatorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilizator)))
            .andExpect(status().isBadRequest());

        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUtilizators() throws Exception {
        // Initialize the database
        utilizatorRepository.saveAndFlush(utilizator);

        // Get all the utilizatorList
        restUtilizatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilizator.getId())))
            .andExpect(jsonPath("$.[*].nume").value(hasItem(DEFAULT_NUME)))
            .andExpect(jsonPath("$.[*].prenume").value(hasItem(DEFAULT_PRENUME)))
            .andExpect(jsonPath("$.[*].functie").value(hasItem(DEFAULT_FUNCTIE.toString())));
    }

    @Test
    @Transactional
    void getUtilizator() throws Exception {
        // Initialize the database
        utilizatorRepository.saveAndFlush(utilizator);

        // Get the utilizator
        restUtilizatorMockMvc
            .perform(get(ENTITY_API_URL_ID, utilizator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(utilizator.getId()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME))
            .andExpect(jsonPath("$.prenume").value(DEFAULT_PRENUME))
            .andExpect(jsonPath("$.functie").value(DEFAULT_FUNCTIE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUtilizator() throws Exception {
        // Get the utilizator
        restUtilizatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUtilizator() throws Exception {
        // Initialize the database
        utilizatorRepository.saveAndFlush(utilizator);

        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();

        // Update the utilizator
        Utilizator updatedUtilizator = utilizatorRepository.findById(utilizator.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUtilizator are not directly saved in db
        em.detach(updatedUtilizator);
        updatedUtilizator.nume(UPDATED_NUME).prenume(UPDATED_PRENUME).functie(UPDATED_FUNCTIE);

        restUtilizatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUtilizator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUtilizator))
            )
            .andExpect(status().isOk());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
        Utilizator testUtilizator = utilizatorList.get(utilizatorList.size() - 1);
        assertThat(testUtilizator.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testUtilizator.getPrenume()).isEqualTo(UPDATED_PRENUME);
        assertThat(testUtilizator.getFunctie()).isEqualTo(UPDATED_FUNCTIE);
    }

    @Test
    @Transactional
    void putNonExistingUtilizator() throws Exception {
        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();
        utilizator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilizatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, utilizator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(utilizator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUtilizator() throws Exception {
        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();
        utilizator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilizatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(utilizator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUtilizator() throws Exception {
        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();
        utilizator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilizatorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilizator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUtilizatorWithPatch() throws Exception {
        // Initialize the database
        utilizatorRepository.saveAndFlush(utilizator);

        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();

        // Update the utilizator using partial update
        Utilizator partialUpdatedUtilizator = new Utilizator();
        partialUpdatedUtilizator.setId(utilizator.getId());

        partialUpdatedUtilizator.nume(UPDATED_NUME);

        restUtilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUtilizator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUtilizator))
            )
            .andExpect(status().isOk());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
        Utilizator testUtilizator = utilizatorList.get(utilizatorList.size() - 1);
        assertThat(testUtilizator.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testUtilizator.getPrenume()).isEqualTo(DEFAULT_PRENUME);
        assertThat(testUtilizator.getFunctie()).isEqualTo(DEFAULT_FUNCTIE);
    }

    @Test
    @Transactional
    void fullUpdateUtilizatorWithPatch() throws Exception {
        // Initialize the database
        utilizatorRepository.saveAndFlush(utilizator);

        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();

        // Update the utilizator using partial update
        Utilizator partialUpdatedUtilizator = new Utilizator();
        partialUpdatedUtilizator.setId(utilizator.getId());

        partialUpdatedUtilizator.nume(UPDATED_NUME).prenume(UPDATED_PRENUME).functie(UPDATED_FUNCTIE);

        restUtilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUtilizator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUtilizator))
            )
            .andExpect(status().isOk());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
        Utilizator testUtilizator = utilizatorList.get(utilizatorList.size() - 1);
        assertThat(testUtilizator.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testUtilizator.getPrenume()).isEqualTo(UPDATED_PRENUME);
        assertThat(testUtilizator.getFunctie()).isEqualTo(UPDATED_FUNCTIE);
    }

    @Test
    @Transactional
    void patchNonExistingUtilizator() throws Exception {
        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();
        utilizator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, utilizator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(utilizator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUtilizator() throws Exception {
        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();
        utilizator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(utilizator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUtilizator() throws Exception {
        int databaseSizeBeforeUpdate = utilizatorRepository.findAll().size();
        utilizator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilizatorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(utilizator))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Utilizator in the database
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUtilizator() throws Exception {
        // Initialize the database
        utilizatorRepository.saveAndFlush(utilizator);

        int databaseSizeBeforeDelete = utilizatorRepository.findAll().size();

        // Delete the utilizator
        restUtilizatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, utilizator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Utilizator> utilizatorList = utilizatorRepository.findAll();
        assertThat(utilizatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
