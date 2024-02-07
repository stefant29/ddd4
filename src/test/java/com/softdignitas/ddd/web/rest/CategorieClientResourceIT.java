package com.softdignitas.ddd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.softdignitas.ddd.IntegrationTest;
import com.softdignitas.ddd.domain.CategorieClient;
import com.softdignitas.ddd.repository.CategorieClientRepository;
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
 * Integration tests for the {@link CategorieClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieClientResourceIT {

    private static final String DEFAULT_NUME = "AAAAAAAAAA";
    private static final String UPDATED_NUME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categorie-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CategorieClientRepository categorieClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieClientMockMvc;

    private CategorieClient categorieClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieClient createEntity(EntityManager em) {
        CategorieClient categorieClient = new CategorieClient().nume(DEFAULT_NUME);
        return categorieClient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieClient createUpdatedEntity(EntityManager em) {
        CategorieClient categorieClient = new CategorieClient().nume(UPDATED_NUME);
        return categorieClient;
    }

    @BeforeEach
    public void initTest() {
        categorieClient = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieClient() throws Exception {
        int databaseSizeBeforeCreate = categorieClientRepository.findAll().size();
        // Create the CategorieClient
        restCategorieClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieClient testCategorieClient = categorieClientList.get(categorieClientList.size() - 1);
        assertThat(testCategorieClient.getNume()).isEqualTo(DEFAULT_NUME);
    }

    @Test
    @Transactional
    void createCategorieClientWithExistingId() throws Exception {
        // Create the CategorieClient with an existing ID
        categorieClient.setId("existing_id");

        int databaseSizeBeforeCreate = categorieClientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieClientMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorieClients() throws Exception {
        // Initialize the database
        categorieClientRepository.saveAndFlush(categorieClient);

        // Get all the categorieClientList
        restCategorieClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieClient.getId())))
            .andExpect(jsonPath("$.[*].nume").value(hasItem(DEFAULT_NUME)));
    }

    @Test
    @Transactional
    void getCategorieClient() throws Exception {
        // Initialize the database
        categorieClientRepository.saveAndFlush(categorieClient);

        // Get the categorieClient
        restCategorieClientMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieClient.getId()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME));
    }

    @Test
    @Transactional
    void getNonExistingCategorieClient() throws Exception {
        // Get the categorieClient
        restCategorieClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategorieClient() throws Exception {
        // Initialize the database
        categorieClientRepository.saveAndFlush(categorieClient);

        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();

        // Update the categorieClient
        CategorieClient updatedCategorieClient = categorieClientRepository.findById(categorieClient.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCategorieClient are not directly saved in db
        em.detach(updatedCategorieClient);
        updatedCategorieClient.nume(UPDATED_NUME);

        restCategorieClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCategorieClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCategorieClient))
            )
            .andExpect(status().isOk());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
        CategorieClient testCategorieClient = categorieClientList.get(categorieClientList.size() - 1);
        assertThat(testCategorieClient.getNume()).isEqualTo(UPDATED_NUME);
    }

    @Test
    @Transactional
    void putNonExistingCategorieClient() throws Exception {
        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();
        categorieClient.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieClient() throws Exception {
        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();
        categorieClient.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieClient() throws Exception {
        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();
        categorieClient.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClientMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieClientWithPatch() throws Exception {
        // Initialize the database
        categorieClientRepository.saveAndFlush(categorieClient);

        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();

        // Update the categorieClient using partial update
        CategorieClient partialUpdatedCategorieClient = new CategorieClient();
        partialUpdatedCategorieClient.setId(categorieClient.getId());

        restCategorieClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieClient))
            )
            .andExpect(status().isOk());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
        CategorieClient testCategorieClient = categorieClientList.get(categorieClientList.size() - 1);
        assertThat(testCategorieClient.getNume()).isEqualTo(DEFAULT_NUME);
    }

    @Test
    @Transactional
    void fullUpdateCategorieClientWithPatch() throws Exception {
        // Initialize the database
        categorieClientRepository.saveAndFlush(categorieClient);

        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();

        // Update the categorieClient using partial update
        CategorieClient partialUpdatedCategorieClient = new CategorieClient();
        partialUpdatedCategorieClient.setId(categorieClient.getId());

        partialUpdatedCategorieClient.nume(UPDATED_NUME);

        restCategorieClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieClient))
            )
            .andExpect(status().isOk());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
        CategorieClient testCategorieClient = categorieClientList.get(categorieClientList.size() - 1);
        assertThat(testCategorieClient.getNume()).isEqualTo(UPDATED_NUME);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieClient() throws Exception {
        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();
        categorieClient.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieClient() throws Exception {
        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();
        categorieClient.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieClient() throws Exception {
        int databaseSizeBeforeUpdate = categorieClientRepository.findAll().size();
        categorieClient.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieClientMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieClient in the database
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieClient() throws Exception {
        // Initialize the database
        categorieClientRepository.saveAndFlush(categorieClient);

        int databaseSizeBeforeDelete = categorieClientRepository.findAll().size();

        // Delete the categorieClient
        restCategorieClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieClient> categorieClientList = categorieClientRepository.findAll();
        assertThat(categorieClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
