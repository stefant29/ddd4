package com.softdignitas.ddd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.softdignitas.ddd.IntegrationTest;
import com.softdignitas.ddd.domain.JTMaterialProcesVerbal;
import com.softdignitas.ddd.domain.Material;
import com.softdignitas.ddd.domain.ProcesVerbal;
import com.softdignitas.ddd.domain.enumeration.Procedura;
import com.softdignitas.ddd.repository.JTMaterialProcesVerbalRepository;
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
 * Integration tests for the {@link JTMaterialProcesVerbalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JTMaterialProcesVerbalResourceIT {

    private static final Procedura DEFAULT_PROCEDURA = Procedura.DEZINSECTIE;
    private static final Procedura UPDATED_PROCEDURA = Procedura.DEZINFECTIE;

    private static final Integer DEFAULT_CANTITATE = 1;
    private static final Integer UPDATED_CANTITATE = 2;

    private static final String ENTITY_API_URL = "/api/jt-material-proces-verbals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private JTMaterialProcesVerbalRepository jTMaterialProcesVerbalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJTMaterialProcesVerbalMockMvc;

    private JTMaterialProcesVerbal jTMaterialProcesVerbal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JTMaterialProcesVerbal createEntity(EntityManager em) {
        JTMaterialProcesVerbal jTMaterialProcesVerbal = new JTMaterialProcesVerbal()
            .procedura(DEFAULT_PROCEDURA)
            .cantitate(DEFAULT_CANTITATE);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        jTMaterialProcesVerbal.setProdus(material);
        // Add required entity
        ProcesVerbal procesVerbal;
        if (TestUtil.findAll(em, ProcesVerbal.class).isEmpty()) {
            procesVerbal = ProcesVerbalResourceIT.createEntity(em);
            em.persist(procesVerbal);
            em.flush();
        } else {
            procesVerbal = TestUtil.findAll(em, ProcesVerbal.class).get(0);
        }
        jTMaterialProcesVerbal.setProcesVerbal(procesVerbal);
        return jTMaterialProcesVerbal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JTMaterialProcesVerbal createUpdatedEntity(EntityManager em) {
        JTMaterialProcesVerbal jTMaterialProcesVerbal = new JTMaterialProcesVerbal()
            .procedura(UPDATED_PROCEDURA)
            .cantitate(UPDATED_CANTITATE);
        // Add required entity
        Material material;
        if (TestUtil.findAll(em, Material.class).isEmpty()) {
            material = MaterialResourceIT.createUpdatedEntity(em);
            em.persist(material);
            em.flush();
        } else {
            material = TestUtil.findAll(em, Material.class).get(0);
        }
        jTMaterialProcesVerbal.setProdus(material);
        // Add required entity
        ProcesVerbal procesVerbal;
        if (TestUtil.findAll(em, ProcesVerbal.class).isEmpty()) {
            procesVerbal = ProcesVerbalResourceIT.createUpdatedEntity(em);
            em.persist(procesVerbal);
            em.flush();
        } else {
            procesVerbal = TestUtil.findAll(em, ProcesVerbal.class).get(0);
        }
        jTMaterialProcesVerbal.setProcesVerbal(procesVerbal);
        return jTMaterialProcesVerbal;
    }

    @BeforeEach
    public void initTest() {
        jTMaterialProcesVerbal = createEntity(em);
    }

    @Test
    @Transactional
    void createJTMaterialProcesVerbal() throws Exception {
        int databaseSizeBeforeCreate = jTMaterialProcesVerbalRepository.findAll().size();
        // Create the JTMaterialProcesVerbal
        restJTMaterialProcesVerbalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isCreated());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeCreate + 1);
        JTMaterialProcesVerbal testJTMaterialProcesVerbal = jTMaterialProcesVerbalList.get(jTMaterialProcesVerbalList.size() - 1);
        assertThat(testJTMaterialProcesVerbal.getProcedura()).isEqualTo(DEFAULT_PROCEDURA);
        assertThat(testJTMaterialProcesVerbal.getCantitate()).isEqualTo(DEFAULT_CANTITATE);
    }

    @Test
    @Transactional
    void createJTMaterialProcesVerbalWithExistingId() throws Exception {
        // Create the JTMaterialProcesVerbal with an existing ID
        jTMaterialProcesVerbal.setId("existing_id");

        int databaseSizeBeforeCreate = jTMaterialProcesVerbalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJTMaterialProcesVerbalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJTMaterialProcesVerbals() throws Exception {
        // Initialize the database
        jTMaterialProcesVerbalRepository.saveAndFlush(jTMaterialProcesVerbal);

        // Get all the jTMaterialProcesVerbalList
        restJTMaterialProcesVerbalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jTMaterialProcesVerbal.getId())))
            .andExpect(jsonPath("$.[*].procedura").value(hasItem(DEFAULT_PROCEDURA.toString())))
            .andExpect(jsonPath("$.[*].cantitate").value(hasItem(DEFAULT_CANTITATE)));
    }

    @Test
    @Transactional
    void getJTMaterialProcesVerbal() throws Exception {
        // Initialize the database
        jTMaterialProcesVerbalRepository.saveAndFlush(jTMaterialProcesVerbal);

        // Get the jTMaterialProcesVerbal
        restJTMaterialProcesVerbalMockMvc
            .perform(get(ENTITY_API_URL_ID, jTMaterialProcesVerbal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jTMaterialProcesVerbal.getId()))
            .andExpect(jsonPath("$.procedura").value(DEFAULT_PROCEDURA.toString()))
            .andExpect(jsonPath("$.cantitate").value(DEFAULT_CANTITATE));
    }

    @Test
    @Transactional
    void getNonExistingJTMaterialProcesVerbal() throws Exception {
        // Get the jTMaterialProcesVerbal
        restJTMaterialProcesVerbalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJTMaterialProcesVerbal() throws Exception {
        // Initialize the database
        jTMaterialProcesVerbalRepository.saveAndFlush(jTMaterialProcesVerbal);

        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();

        // Update the jTMaterialProcesVerbal
        JTMaterialProcesVerbal updatedJTMaterialProcesVerbal = jTMaterialProcesVerbalRepository
            .findById(jTMaterialProcesVerbal.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedJTMaterialProcesVerbal are not directly saved in db
        em.detach(updatedJTMaterialProcesVerbal);
        updatedJTMaterialProcesVerbal.procedura(UPDATED_PROCEDURA).cantitate(UPDATED_CANTITATE);

        restJTMaterialProcesVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJTMaterialProcesVerbal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJTMaterialProcesVerbal))
            )
            .andExpect(status().isOk());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
        JTMaterialProcesVerbal testJTMaterialProcesVerbal = jTMaterialProcesVerbalList.get(jTMaterialProcesVerbalList.size() - 1);
        assertThat(testJTMaterialProcesVerbal.getProcedura()).isEqualTo(UPDATED_PROCEDURA);
        assertThat(testJTMaterialProcesVerbal.getCantitate()).isEqualTo(UPDATED_CANTITATE);
    }

    @Test
    @Transactional
    void putNonExistingJTMaterialProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();
        jTMaterialProcesVerbal.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJTMaterialProcesVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jTMaterialProcesVerbal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJTMaterialProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();
        jTMaterialProcesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJTMaterialProcesVerbalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJTMaterialProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();
        jTMaterialProcesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJTMaterialProcesVerbalMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJTMaterialProcesVerbalWithPatch() throws Exception {
        // Initialize the database
        jTMaterialProcesVerbalRepository.saveAndFlush(jTMaterialProcesVerbal);

        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();

        // Update the jTMaterialProcesVerbal using partial update
        JTMaterialProcesVerbal partialUpdatedJTMaterialProcesVerbal = new JTMaterialProcesVerbal();
        partialUpdatedJTMaterialProcesVerbal.setId(jTMaterialProcesVerbal.getId());

        partialUpdatedJTMaterialProcesVerbal.cantitate(UPDATED_CANTITATE);

        restJTMaterialProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJTMaterialProcesVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJTMaterialProcesVerbal))
            )
            .andExpect(status().isOk());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
        JTMaterialProcesVerbal testJTMaterialProcesVerbal = jTMaterialProcesVerbalList.get(jTMaterialProcesVerbalList.size() - 1);
        assertThat(testJTMaterialProcesVerbal.getProcedura()).isEqualTo(DEFAULT_PROCEDURA);
        assertThat(testJTMaterialProcesVerbal.getCantitate()).isEqualTo(UPDATED_CANTITATE);
    }

    @Test
    @Transactional
    void fullUpdateJTMaterialProcesVerbalWithPatch() throws Exception {
        // Initialize the database
        jTMaterialProcesVerbalRepository.saveAndFlush(jTMaterialProcesVerbal);

        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();

        // Update the jTMaterialProcesVerbal using partial update
        JTMaterialProcesVerbal partialUpdatedJTMaterialProcesVerbal = new JTMaterialProcesVerbal();
        partialUpdatedJTMaterialProcesVerbal.setId(jTMaterialProcesVerbal.getId());

        partialUpdatedJTMaterialProcesVerbal.procedura(UPDATED_PROCEDURA).cantitate(UPDATED_CANTITATE);

        restJTMaterialProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJTMaterialProcesVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJTMaterialProcesVerbal))
            )
            .andExpect(status().isOk());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
        JTMaterialProcesVerbal testJTMaterialProcesVerbal = jTMaterialProcesVerbalList.get(jTMaterialProcesVerbalList.size() - 1);
        assertThat(testJTMaterialProcesVerbal.getProcedura()).isEqualTo(UPDATED_PROCEDURA);
        assertThat(testJTMaterialProcesVerbal.getCantitate()).isEqualTo(UPDATED_CANTITATE);
    }

    @Test
    @Transactional
    void patchNonExistingJTMaterialProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();
        jTMaterialProcesVerbal.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJTMaterialProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jTMaterialProcesVerbal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJTMaterialProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();
        jTMaterialProcesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJTMaterialProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isBadRequest());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJTMaterialProcesVerbal() throws Exception {
        int databaseSizeBeforeUpdate = jTMaterialProcesVerbalRepository.findAll().size();
        jTMaterialProcesVerbal.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJTMaterialProcesVerbalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jTMaterialProcesVerbal))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JTMaterialProcesVerbal in the database
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJTMaterialProcesVerbal() throws Exception {
        // Initialize the database
        jTMaterialProcesVerbalRepository.saveAndFlush(jTMaterialProcesVerbal);

        int databaseSizeBeforeDelete = jTMaterialProcesVerbalRepository.findAll().size();

        // Delete the jTMaterialProcesVerbal
        restJTMaterialProcesVerbalMockMvc
            .perform(delete(ENTITY_API_URL_ID, jTMaterialProcesVerbal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JTMaterialProcesVerbal> jTMaterialProcesVerbalList = jTMaterialProcesVerbalRepository.findAll();
        assertThat(jTMaterialProcesVerbalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
