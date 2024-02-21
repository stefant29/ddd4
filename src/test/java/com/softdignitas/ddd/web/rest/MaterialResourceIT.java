package com.softdignitas.ddd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.softdignitas.ddd.IntegrationTest;
import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.domain.Material;
import com.softdignitas.ddd.domain.enumeration.Procedura;
import com.softdignitas.ddd.repository.MaterialRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link MaterialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialResourceIT {

    private static final Procedura DEFAULT_PROCEDURA = Procedura.DEZINSECTIE;
    private static final Procedura UPDATED_PROCEDURA = Procedura.DEZINFECTIE;

    private static final String DEFAULT_FACTURA = "AAAAAAAAAA";
    private static final String UPDATED_FACTURA = "BBBBBBBBBB";

    private static final String DEFAULT_DENUMIRE = "AAAAAAAAAA";
    private static final String UPDATED_DENUMIRE = "BBBBBBBBBB";

    private static final String DEFAULT_LOT = "AAAAAAAAAA";
    private static final String UPDATED_LOT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_ACHIZITIONARE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ACHIZITIONARE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_EXPIRARE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EXPIRARE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DILUTIE = "AAAAAAAAAA";
    private static final String UPDATED_DILUTIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMP_CONTACT = 1;
    private static final Integer UPDATED_TIMP_CONTACT = 2;

    private static final String DEFAULT_METODA_APLICARE = "AAAAAAAAAA";
    private static final String UPDATED_METODA_APLICARE = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRAMAJ = 1;
    private static final Integer UPDATED_GRAMAJ = 2;

    private static final Integer DEFAULT_CANTITATE = 1;
    private static final Integer UPDATED_CANTITATE = 2;

    private static final String ENTITY_API_URL = "/api/materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialMockMvc;

    private Material material;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createEntity(EntityManager em) {
        Material material = new Material()
            .procedura(DEFAULT_PROCEDURA)
            .factura(DEFAULT_FACTURA)
            .denumire(DEFAULT_DENUMIRE)
            .lot(DEFAULT_LOT)
            .dataAchizitionare(DEFAULT_DATA_ACHIZITIONARE)
            .dataExpirare(DEFAULT_DATA_EXPIRARE)
            .dilutie(DEFAULT_DILUTIE)
            .timpContact(DEFAULT_TIMP_CONTACT)
            .metodaAplicare(DEFAULT_METODA_APLICARE)
            .gramaj(DEFAULT_GRAMAJ)
            .cantitate(DEFAULT_CANTITATE);
        // Add required entity
        Companie companie;
        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
            companie = CompanieResourceIT.createEntity(em);
            em.persist(companie);
            em.flush();
        } else {
            companie = TestUtil.findAll(em, Companie.class).get(0);
        }
        material.setCompanie(companie);
        return material;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Material createUpdatedEntity(EntityManager em) {
        Material material = new Material()
            .procedura(UPDATED_PROCEDURA)
            .factura(UPDATED_FACTURA)
            .denumire(UPDATED_DENUMIRE)
            .lot(UPDATED_LOT)
            .dataAchizitionare(UPDATED_DATA_ACHIZITIONARE)
            .dataExpirare(UPDATED_DATA_EXPIRARE)
            .dilutie(UPDATED_DILUTIE)
            .timpContact(UPDATED_TIMP_CONTACT)
            .metodaAplicare(UPDATED_METODA_APLICARE)
            .gramaj(UPDATED_GRAMAJ)
            .cantitate(UPDATED_CANTITATE);
        // Add required entity
        Companie companie;
        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
            companie = CompanieResourceIT.createUpdatedEntity(em);
            em.persist(companie);
            em.flush();
        } else {
            companie = TestUtil.findAll(em, Companie.class).get(0);
        }
        material.setCompanie(companie);
        return material;
    }

    @BeforeEach
    public void initTest() {
        material = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterial() throws Exception {
        int databaseSizeBeforeCreate = materialRepository.findAll().size();
        // Create the Material
        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isCreated());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate + 1);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getProcedura()).isEqualTo(DEFAULT_PROCEDURA);
        assertThat(testMaterial.getFactura()).isEqualTo(DEFAULT_FACTURA);
        assertThat(testMaterial.getDenumire()).isEqualTo(DEFAULT_DENUMIRE);
        assertThat(testMaterial.getLot()).isEqualTo(DEFAULT_LOT);
        assertThat(testMaterial.getDataAchizitionare()).isEqualTo(DEFAULT_DATA_ACHIZITIONARE);
        assertThat(testMaterial.getDataExpirare()).isEqualTo(DEFAULT_DATA_EXPIRARE);
        assertThat(testMaterial.getDilutie()).isEqualTo(DEFAULT_DILUTIE);
        assertThat(testMaterial.getTimpContact()).isEqualTo(DEFAULT_TIMP_CONTACT);
        assertThat(testMaterial.getMetodaAplicare()).isEqualTo(DEFAULT_METODA_APLICARE);
        assertThat(testMaterial.getGramaj()).isEqualTo(DEFAULT_GRAMAJ);
        assertThat(testMaterial.getCantitate()).isEqualTo(DEFAULT_CANTITATE);
    }

    @Test
    @Transactional
    void createMaterialWithExistingId() throws Exception {
        // Create the Material with an existing ID
        material.setId("existing_id");

        int databaseSizeBeforeCreate = materialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFacturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setFactura(null);

        // Create the Material, which fails.

        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDenumireIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setDenumire(null);

        // Create the Material, which fails.

        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLotIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialRepository.findAll().size();
        // set the field null
        material.setLot(null);

        // Create the Material, which fails.

        restMaterialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isBadRequest());

        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaterials() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get all the materialList
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(material.getId())))
            .andExpect(jsonPath("$.[*].procedura").value(hasItem(DEFAULT_PROCEDURA.toString())))
            .andExpect(jsonPath("$.[*].factura").value(hasItem(DEFAULT_FACTURA)))
            .andExpect(jsonPath("$.[*].denumire").value(hasItem(DEFAULT_DENUMIRE)))
            .andExpect(jsonPath("$.[*].lot").value(hasItem(DEFAULT_LOT)))
            .andExpect(jsonPath("$.[*].dataAchizitionare").value(hasItem(DEFAULT_DATA_ACHIZITIONARE.toString())))
            .andExpect(jsonPath("$.[*].dataExpirare").value(hasItem(DEFAULT_DATA_EXPIRARE.toString())))
            .andExpect(jsonPath("$.[*].dilutie").value(hasItem(DEFAULT_DILUTIE)))
            .andExpect(jsonPath("$.[*].timpContact").value(hasItem(DEFAULT_TIMP_CONTACT)))
            .andExpect(jsonPath("$.[*].metodaAplicare").value(hasItem(DEFAULT_METODA_APLICARE)))
            .andExpect(jsonPath("$.[*].gramaj").value(hasItem(DEFAULT_GRAMAJ)))
            .andExpect(jsonPath("$.[*].cantitate").value(hasItem(DEFAULT_CANTITATE)));
    }

    @Test
    @Transactional
    void getMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        // Get the material
        restMaterialMockMvc
            .perform(get(ENTITY_API_URL_ID, material.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(material.getId()))
            .andExpect(jsonPath("$.procedura").value(DEFAULT_PROCEDURA.toString()))
            .andExpect(jsonPath("$.factura").value(DEFAULT_FACTURA))
            .andExpect(jsonPath("$.denumire").value(DEFAULT_DENUMIRE))
            .andExpect(jsonPath("$.lot").value(DEFAULT_LOT))
            .andExpect(jsonPath("$.dataAchizitionare").value(DEFAULT_DATA_ACHIZITIONARE.toString()))
            .andExpect(jsonPath("$.dataExpirare").value(DEFAULT_DATA_EXPIRARE.toString()))
            .andExpect(jsonPath("$.dilutie").value(DEFAULT_DILUTIE))
            .andExpect(jsonPath("$.timpContact").value(DEFAULT_TIMP_CONTACT))
            .andExpect(jsonPath("$.metodaAplicare").value(DEFAULT_METODA_APLICARE))
            .andExpect(jsonPath("$.gramaj").value(DEFAULT_GRAMAJ))
            .andExpect(jsonPath("$.cantitate").value(DEFAULT_CANTITATE));
    }

    @Test
    @Transactional
    void getNonExistingMaterial() throws Exception {
        // Get the material
        restMaterialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material
        Material updatedMaterial = materialRepository.findById(material.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMaterial are not directly saved in db
        em.detach(updatedMaterial);
        updatedMaterial
            .procedura(UPDATED_PROCEDURA)
            .factura(UPDATED_FACTURA)
            .denumire(UPDATED_DENUMIRE)
            .lot(UPDATED_LOT)
            .dataAchizitionare(UPDATED_DATA_ACHIZITIONARE)
            .dataExpirare(UPDATED_DATA_EXPIRARE)
            .dilutie(UPDATED_DILUTIE)
            .timpContact(UPDATED_TIMP_CONTACT)
            .metodaAplicare(UPDATED_METODA_APLICARE)
            .gramaj(UPDATED_GRAMAJ)
            .cantitate(UPDATED_CANTITATE);

        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterial.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getProcedura()).isEqualTo(UPDATED_PROCEDURA);
        assertThat(testMaterial.getFactura()).isEqualTo(UPDATED_FACTURA);
        assertThat(testMaterial.getDenumire()).isEqualTo(UPDATED_DENUMIRE);
        assertThat(testMaterial.getLot()).isEqualTo(UPDATED_LOT);
        assertThat(testMaterial.getDataAchizitionare()).isEqualTo(UPDATED_DATA_ACHIZITIONARE);
        assertThat(testMaterial.getDataExpirare()).isEqualTo(UPDATED_DATA_EXPIRARE);
        assertThat(testMaterial.getDilutie()).isEqualTo(UPDATED_DILUTIE);
        assertThat(testMaterial.getTimpContact()).isEqualTo(UPDATED_TIMP_CONTACT);
        assertThat(testMaterial.getMetodaAplicare()).isEqualTo(UPDATED_METODA_APLICARE);
        assertThat(testMaterial.getGramaj()).isEqualTo(UPDATED_GRAMAJ);
        assertThat(testMaterial.getCantitate()).isEqualTo(UPDATED_CANTITATE);
    }

    @Test
    @Transactional
    void putNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, material.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial.factura(UPDATED_FACTURA).dilutie(UPDATED_DILUTIE).cantitate(UPDATED_CANTITATE);

        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getProcedura()).isEqualTo(DEFAULT_PROCEDURA);
        assertThat(testMaterial.getFactura()).isEqualTo(UPDATED_FACTURA);
        assertThat(testMaterial.getDenumire()).isEqualTo(DEFAULT_DENUMIRE);
        assertThat(testMaterial.getLot()).isEqualTo(DEFAULT_LOT);
        assertThat(testMaterial.getDataAchizitionare()).isEqualTo(DEFAULT_DATA_ACHIZITIONARE);
        assertThat(testMaterial.getDataExpirare()).isEqualTo(DEFAULT_DATA_EXPIRARE);
        assertThat(testMaterial.getDilutie()).isEqualTo(UPDATED_DILUTIE);
        assertThat(testMaterial.getTimpContact()).isEqualTo(DEFAULT_TIMP_CONTACT);
        assertThat(testMaterial.getMetodaAplicare()).isEqualTo(DEFAULT_METODA_APLICARE);
        assertThat(testMaterial.getGramaj()).isEqualTo(DEFAULT_GRAMAJ);
        assertThat(testMaterial.getCantitate()).isEqualTo(UPDATED_CANTITATE);
    }

    @Test
    @Transactional
    void fullUpdateMaterialWithPatch() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeUpdate = materialRepository.findAll().size();

        // Update the material using partial update
        Material partialUpdatedMaterial = new Material();
        partialUpdatedMaterial.setId(material.getId());

        partialUpdatedMaterial
            .procedura(UPDATED_PROCEDURA)
            .factura(UPDATED_FACTURA)
            .denumire(UPDATED_DENUMIRE)
            .lot(UPDATED_LOT)
            .dataAchizitionare(UPDATED_DATA_ACHIZITIONARE)
            .dataExpirare(UPDATED_DATA_EXPIRARE)
            .dilutie(UPDATED_DILUTIE)
            .timpContact(UPDATED_TIMP_CONTACT)
            .metodaAplicare(UPDATED_METODA_APLICARE)
            .gramaj(UPDATED_GRAMAJ)
            .cantitate(UPDATED_CANTITATE);

        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterial))
            )
            .andExpect(status().isOk());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
        Material testMaterial = materialList.get(materialList.size() - 1);
        assertThat(testMaterial.getProcedura()).isEqualTo(UPDATED_PROCEDURA);
        assertThat(testMaterial.getFactura()).isEqualTo(UPDATED_FACTURA);
        assertThat(testMaterial.getDenumire()).isEqualTo(UPDATED_DENUMIRE);
        assertThat(testMaterial.getLot()).isEqualTo(UPDATED_LOT);
        assertThat(testMaterial.getDataAchizitionare()).isEqualTo(UPDATED_DATA_ACHIZITIONARE);
        assertThat(testMaterial.getDataExpirare()).isEqualTo(UPDATED_DATA_EXPIRARE);
        assertThat(testMaterial.getDilutie()).isEqualTo(UPDATED_DILUTIE);
        assertThat(testMaterial.getTimpContact()).isEqualTo(UPDATED_TIMP_CONTACT);
        assertThat(testMaterial.getMetodaAplicare()).isEqualTo(UPDATED_METODA_APLICARE);
        assertThat(testMaterial.getGramaj()).isEqualTo(UPDATED_GRAMAJ);
        assertThat(testMaterial.getCantitate()).isEqualTo(UPDATED_CANTITATE);
    }

    @Test
    @Transactional
    void patchNonExistingMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, material.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(material))
            )
            .andExpect(status().isBadRequest());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterial() throws Exception {
        int databaseSizeBeforeUpdate = materialRepository.findAll().size();
        material.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(material)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Material in the database
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterial() throws Exception {
        // Initialize the database
        materialRepository.saveAndFlush(material);

        int databaseSizeBeforeDelete = materialRepository.findAll().size();

        // Delete the material
        restMaterialMockMvc
            .perform(delete(ENTITY_API_URL_ID, material.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Material> materialList = materialRepository.findAll();
        assertThat(materialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
