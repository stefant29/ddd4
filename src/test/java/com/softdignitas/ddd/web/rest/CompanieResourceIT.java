package com.softdignitas.ddd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.softdignitas.ddd.IntegrationTest;
import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.repository.CompanieRepository;
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
 * Integration tests for the {@link CompanieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanieResourceIT {

    private static final String DEFAULT_NUME = "AAAAAAAAAA";
    private static final String UPDATED_NUME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CompanieRepository companieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanieMockMvc;

    private Companie companie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companie createEntity(EntityManager em) {
        Companie companie = new Companie().nume(DEFAULT_NUME);
        return companie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Companie createUpdatedEntity(EntityManager em) {
        Companie companie = new Companie().nume(UPDATED_NUME);
        return companie;
    }

    @BeforeEach
    public void initTest() {
        companie = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanie() throws Exception {
        int databaseSizeBeforeCreate = companieRepository.findAll().size();
        // Create the Companie
        restCompanieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companie)))
            .andExpect(status().isCreated());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeCreate + 1);
        Companie testCompanie = companieList.get(companieList.size() - 1);
        assertThat(testCompanie.getNume()).isEqualTo(DEFAULT_NUME);
    }

    @Test
    @Transactional
    void createCompanieWithExistingId() throws Exception {
        // Create the Companie with an existing ID
        companie.setId("existing_id");

        int databaseSizeBeforeCreate = companieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companie)))
            .andExpect(status().isBadRequest());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        // Get all the companieList
        restCompanieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companie.getId())))
            .andExpect(jsonPath("$.[*].nume").value(hasItem(DEFAULT_NUME)));
    }

    @Test
    @Transactional
    void getCompanie() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        // Get the companie
        restCompanieMockMvc
            .perform(get(ENTITY_API_URL_ID, companie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companie.getId()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME));
    }

    @Test
    @Transactional
    void getNonExistingCompanie() throws Exception {
        // Get the companie
        restCompanieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompanie() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        int databaseSizeBeforeUpdate = companieRepository.findAll().size();

        // Update the companie
        Companie updatedCompanie = companieRepository.findById(companie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompanie are not directly saved in db
        em.detach(updatedCompanie);
        updatedCompanie.nume(UPDATED_NUME);

        restCompanieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompanie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompanie))
            )
            .andExpect(status().isOk());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
        Companie testCompanie = companieList.get(companieList.size() - 1);
        assertThat(testCompanie.getNume()).isEqualTo(UPDATED_NUME);
    }

    @Test
    @Transactional
    void putNonExistingCompanie() throws Exception {
        int databaseSizeBeforeUpdate = companieRepository.findAll().size();
        companie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanie() throws Exception {
        int databaseSizeBeforeUpdate = companieRepository.findAll().size();
        companie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanie() throws Exception {
        int databaseSizeBeforeUpdate = companieRepository.findAll().size();
        companie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanieWithPatch() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        int databaseSizeBeforeUpdate = companieRepository.findAll().size();

        // Update the companie using partial update
        Companie partialUpdatedCompanie = new Companie();
        partialUpdatedCompanie.setId(companie.getId());

        partialUpdatedCompanie.nume(UPDATED_NUME);

        restCompanieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanie))
            )
            .andExpect(status().isOk());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
        Companie testCompanie = companieList.get(companieList.size() - 1);
        assertThat(testCompanie.getNume()).isEqualTo(UPDATED_NUME);
    }

    @Test
    @Transactional
    void fullUpdateCompanieWithPatch() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        int databaseSizeBeforeUpdate = companieRepository.findAll().size();

        // Update the companie using partial update
        Companie partialUpdatedCompanie = new Companie();
        partialUpdatedCompanie.setId(companie.getId());

        partialUpdatedCompanie.nume(UPDATED_NUME);

        restCompanieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanie))
            )
            .andExpect(status().isOk());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
        Companie testCompanie = companieList.get(companieList.size() - 1);
        assertThat(testCompanie.getNume()).isEqualTo(UPDATED_NUME);
    }

    @Test
    @Transactional
    void patchNonExistingCompanie() throws Exception {
        int databaseSizeBeforeUpdate = companieRepository.findAll().size();
        companie.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanie() throws Exception {
        int databaseSizeBeforeUpdate = companieRepository.findAll().size();
        companie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanie() throws Exception {
        int databaseSizeBeforeUpdate = companieRepository.findAll().size();
        companie.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Companie in the database
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanie() throws Exception {
        // Initialize the database
        companieRepository.saveAndFlush(companie);

        int databaseSizeBeforeDelete = companieRepository.findAll().size();

        // Delete the companie
        restCompanieMockMvc
            .perform(delete(ENTITY_API_URL_ID, companie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Companie> companieList = companieRepository.findAll();
        assertThat(companieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
