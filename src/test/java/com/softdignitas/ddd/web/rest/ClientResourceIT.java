package com.softdignitas.ddd.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.softdignitas.ddd.IntegrationTest;
import com.softdignitas.ddd.domain.Client;
import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.repository.ClientRepository;
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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_DENUMIRE = "AAAAAAAAAA";
    private static final String UPDATED_DENUMIRE = "BBBBBBBBBB";

    private static final String DEFAULT_COD_FISCAL = "AAAAAAAAAA";
    private static final String UPDATED_COD_FISCAL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMAR_REGISTRU_COMERT = "AAAAAAAAAA";
    private static final String UPDATED_NUMAR_REGISTRU_COMERT = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESA_PUNCT_LUCRU = "AAAAAAAAAA";
    private static final String UPDATED_ADRESA_PUNCT_LUCRU = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PERSOANA_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_PERSOANA_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DERATIZARE = false;
    private static final Boolean UPDATED_DERATIZARE = true;

    private static final Boolean DEFAULT_DEZINSECTIE = false;
    private static final Boolean UPDATED_DEZINSECTIE = true;

    private static final Boolean DEFAULT_DEZINFECTIE = false;
    private static final Boolean UPDATED_DEZINFECTIE = true;

    private static final Integer DEFAULT_FRECVENTA_DERATIZARE = 1;
    private static final Integer UPDATED_FRECVENTA_DERATIZARE = 2;

    private static final Integer DEFAULT_FRECVENTA_DEZINSECTIE = 1;
    private static final Integer UPDATED_FRECVENTA_DEZINSECTIE = 2;

    private static final Integer DEFAULT_FRECVENTA_DEZINFECTIE = 1;
    private static final Integer UPDATED_FRECVENTA_DEZINFECTIE = 2;

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .denumire(DEFAULT_DENUMIRE)
            .codFiscal(DEFAULT_COD_FISCAL)
            .numarRegistruComert(DEFAULT_NUMAR_REGISTRU_COMERT)
            .adresaPunctLucru(DEFAULT_ADRESA_PUNCT_LUCRU)
            .telefon(DEFAULT_TELEFON)
            .email(DEFAULT_EMAIL)
            .persoanaContact(DEFAULT_PERSOANA_CONTACT)
            .contract(DEFAULT_CONTRACT)
            .deratizare(DEFAULT_DERATIZARE)
            .dezinsectie(DEFAULT_DEZINSECTIE)
            .dezinfectie(DEFAULT_DEZINFECTIE)
            .frecventaDeratizare(DEFAULT_FRECVENTA_DERATIZARE)
            .frecventaDezinsectie(DEFAULT_FRECVENTA_DEZINSECTIE)
            .frecventaDezinfectie(DEFAULT_FRECVENTA_DEZINFECTIE);
        //        // Add required entity
        //        Companie companie;
        //        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
        //            companie = CompanieResourceIT.createEntity(em);
        //            em.persist(companie);
        //            em.flush();
        //        } else {
        //            companie = TestUtil.findAll(em, Companie.class).get(0);
        //        }
        //        client.setCompanie(companie);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .denumire(UPDATED_DENUMIRE)
            .codFiscal(UPDATED_COD_FISCAL)
            .numarRegistruComert(UPDATED_NUMAR_REGISTRU_COMERT)
            .adresaPunctLucru(UPDATED_ADRESA_PUNCT_LUCRU)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .persoanaContact(UPDATED_PERSOANA_CONTACT)
            .contract(UPDATED_CONTRACT)
            .deratizare(UPDATED_DERATIZARE)
            .dezinsectie(UPDATED_DEZINSECTIE)
            .dezinfectie(UPDATED_DEZINFECTIE)
            .frecventaDeratizare(UPDATED_FRECVENTA_DERATIZARE)
            .frecventaDezinsectie(UPDATED_FRECVENTA_DEZINSECTIE)
            .frecventaDezinfectie(UPDATED_FRECVENTA_DEZINFECTIE);
        //        // Add required entity
        //        Companie companie;
        //        if (TestUtil.findAll(em, Companie.class).isEmpty()) {
        //            companie = CompanieResourceIT.createUpdatedEntity(em);
        //            em.persist(companie);
        //            em.flush();
        //        } else {
        //            companie = TestUtil.findAll(em, Companie.class).get(0);
        //        }
        //        client.setCompanie(companie);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDenumire()).isEqualTo(DEFAULT_DENUMIRE);
        assertThat(testClient.getCodFiscal()).isEqualTo(DEFAULT_COD_FISCAL);
        assertThat(testClient.getNumarRegistruComert()).isEqualTo(DEFAULT_NUMAR_REGISTRU_COMERT);
        assertThat(testClient.getAdresaPunctLucru()).isEqualTo(DEFAULT_ADRESA_PUNCT_LUCRU);
        assertThat(testClient.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getPersoanaContact()).isEqualTo(DEFAULT_PERSOANA_CONTACT);
        assertThat(testClient.getContract()).isEqualTo(DEFAULT_CONTRACT);
        assertThat(testClient.getDeratizare()).isEqualTo(DEFAULT_DERATIZARE);
        assertThat(testClient.getDezinsectie()).isEqualTo(DEFAULT_DEZINSECTIE);
        assertThat(testClient.getDezinfectie()).isEqualTo(DEFAULT_DEZINFECTIE);
        assertThat(testClient.getFrecventaDeratizare()).isEqualTo(DEFAULT_FRECVENTA_DERATIZARE);
        assertThat(testClient.getFrecventaDezinsectie()).isEqualTo(DEFAULT_FRECVENTA_DEZINSECTIE);
        assertThat(testClient.getFrecventaDezinfectie()).isEqualTo(DEFAULT_FRECVENTA_DEZINFECTIE);
    }

    @Test
    @Transactional
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId("existing_id");

        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDenumireIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setDenumire(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodFiscalIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setCodFiscal(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdresaPunctLucruIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setAdresaPunctLucru(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setTelefon(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPersoanaContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setPersoanaContact(null);

        // Create the Client, which fails.

        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId())))
            .andExpect(jsonPath("$.[*].denumire").value(hasItem(DEFAULT_DENUMIRE)))
            .andExpect(jsonPath("$.[*].codFiscal").value(hasItem(DEFAULT_COD_FISCAL)))
            .andExpect(jsonPath("$.[*].numarRegistruComert").value(hasItem(DEFAULT_NUMAR_REGISTRU_COMERT)))
            .andExpect(jsonPath("$.[*].adresaPunctLucru").value(hasItem(DEFAULT_ADRESA_PUNCT_LUCRU)))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].persoanaContact").value(hasItem(DEFAULT_PERSOANA_CONTACT)))
            .andExpect(jsonPath("$.[*].contract").value(hasItem(DEFAULT_CONTRACT)))
            .andExpect(jsonPath("$.[*].deratizare").value(hasItem(DEFAULT_DERATIZARE.booleanValue())))
            .andExpect(jsonPath("$.[*].dezinsectie").value(hasItem(DEFAULT_DEZINSECTIE.booleanValue())))
            .andExpect(jsonPath("$.[*].dezinfectie").value(hasItem(DEFAULT_DEZINFECTIE.booleanValue())))
            .andExpect(jsonPath("$.[*].frecventaDeratizare").value(hasItem(DEFAULT_FRECVENTA_DERATIZARE)))
            .andExpect(jsonPath("$.[*].frecventaDezinsectie").value(hasItem(DEFAULT_FRECVENTA_DEZINSECTIE)))
            .andExpect(jsonPath("$.[*].frecventaDezinfectie").value(hasItem(DEFAULT_FRECVENTA_DEZINFECTIE)));
    }

    @Test
    @Transactional
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId()))
            .andExpect(jsonPath("$.denumire").value(DEFAULT_DENUMIRE))
            .andExpect(jsonPath("$.codFiscal").value(DEFAULT_COD_FISCAL))
            .andExpect(jsonPath("$.numarRegistruComert").value(DEFAULT_NUMAR_REGISTRU_COMERT))
            .andExpect(jsonPath("$.adresaPunctLucru").value(DEFAULT_ADRESA_PUNCT_LUCRU))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.persoanaContact").value(DEFAULT_PERSOANA_CONTACT))
            .andExpect(jsonPath("$.contract").value(DEFAULT_CONTRACT))
            .andExpect(jsonPath("$.deratizare").value(DEFAULT_DERATIZARE.booleanValue()))
            .andExpect(jsonPath("$.dezinsectie").value(DEFAULT_DEZINSECTIE.booleanValue()))
            .andExpect(jsonPath("$.dezinfectie").value(DEFAULT_DEZINFECTIE.booleanValue()))
            .andExpect(jsonPath("$.frecventaDeratizare").value(DEFAULT_FRECVENTA_DERATIZARE))
            .andExpect(jsonPath("$.frecventaDezinsectie").value(DEFAULT_FRECVENTA_DEZINSECTIE))
            .andExpect(jsonPath("$.frecventaDezinfectie").value(DEFAULT_FRECVENTA_DEZINFECTIE));
    }

    @Test
    @Transactional
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .denumire(UPDATED_DENUMIRE)
            .codFiscal(UPDATED_COD_FISCAL)
            .numarRegistruComert(UPDATED_NUMAR_REGISTRU_COMERT)
            .adresaPunctLucru(UPDATED_ADRESA_PUNCT_LUCRU)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .persoanaContact(UPDATED_PERSOANA_CONTACT)
            .contract(UPDATED_CONTRACT)
            .deratizare(UPDATED_DERATIZARE)
            .dezinsectie(UPDATED_DEZINSECTIE)
            .dezinfectie(UPDATED_DEZINFECTIE)
            .frecventaDeratizare(UPDATED_FRECVENTA_DERATIZARE)
            .frecventaDezinsectie(UPDATED_FRECVENTA_DEZINSECTIE)
            .frecventaDezinfectie(UPDATED_FRECVENTA_DEZINFECTIE);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDenumire()).isEqualTo(UPDATED_DENUMIRE);
        assertThat(testClient.getCodFiscal()).isEqualTo(UPDATED_COD_FISCAL);
        assertThat(testClient.getNumarRegistruComert()).isEqualTo(UPDATED_NUMAR_REGISTRU_COMERT);
        assertThat(testClient.getAdresaPunctLucru()).isEqualTo(UPDATED_ADRESA_PUNCT_LUCRU);
        assertThat(testClient.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getPersoanaContact()).isEqualTo(UPDATED_PERSOANA_CONTACT);
        assertThat(testClient.getContract()).isEqualTo(UPDATED_CONTRACT);
        assertThat(testClient.getDeratizare()).isEqualTo(UPDATED_DERATIZARE);
        assertThat(testClient.getDezinsectie()).isEqualTo(UPDATED_DEZINSECTIE);
        assertThat(testClient.getDezinfectie()).isEqualTo(UPDATED_DEZINFECTIE);
        assertThat(testClient.getFrecventaDeratizare()).isEqualTo(UPDATED_FRECVENTA_DERATIZARE);
        assertThat(testClient.getFrecventaDezinsectie()).isEqualTo(UPDATED_FRECVENTA_DEZINSECTIE);
        assertThat(testClient.getFrecventaDezinfectie()).isEqualTo(UPDATED_FRECVENTA_DEZINFECTIE);
    }

    @Test
    @Transactional
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient.persoanaContact(UPDATED_PERSOANA_CONTACT).deratizare(UPDATED_DERATIZARE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDenumire()).isEqualTo(DEFAULT_DENUMIRE);
        assertThat(testClient.getCodFiscal()).isEqualTo(DEFAULT_COD_FISCAL);
        assertThat(testClient.getNumarRegistruComert()).isEqualTo(DEFAULT_NUMAR_REGISTRU_COMERT);
        assertThat(testClient.getAdresaPunctLucru()).isEqualTo(DEFAULT_ADRESA_PUNCT_LUCRU);
        assertThat(testClient.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getPersoanaContact()).isEqualTo(UPDATED_PERSOANA_CONTACT);
        assertThat(testClient.getContract()).isEqualTo(DEFAULT_CONTRACT);
        assertThat(testClient.getDeratizare()).isEqualTo(UPDATED_DERATIZARE);
        assertThat(testClient.getDezinsectie()).isEqualTo(DEFAULT_DEZINSECTIE);
        assertThat(testClient.getDezinfectie()).isEqualTo(DEFAULT_DEZINFECTIE);
        assertThat(testClient.getFrecventaDeratizare()).isEqualTo(DEFAULT_FRECVENTA_DERATIZARE);
        assertThat(testClient.getFrecventaDezinsectie()).isEqualTo(DEFAULT_FRECVENTA_DEZINSECTIE);
        assertThat(testClient.getFrecventaDezinfectie()).isEqualTo(DEFAULT_FRECVENTA_DEZINFECTIE);
    }

    @Test
    @Transactional
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .denumire(UPDATED_DENUMIRE)
            .codFiscal(UPDATED_COD_FISCAL)
            .numarRegistruComert(UPDATED_NUMAR_REGISTRU_COMERT)
            .adresaPunctLucru(UPDATED_ADRESA_PUNCT_LUCRU)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .persoanaContact(UPDATED_PERSOANA_CONTACT)
            .contract(UPDATED_CONTRACT)
            .deratizare(UPDATED_DERATIZARE)
            .dezinsectie(UPDATED_DEZINSECTIE)
            .dezinfectie(UPDATED_DEZINFECTIE)
            .frecventaDeratizare(UPDATED_FRECVENTA_DERATIZARE)
            .frecventaDezinsectie(UPDATED_FRECVENTA_DEZINSECTIE)
            .frecventaDezinfectie(UPDATED_FRECVENTA_DEZINFECTIE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getDenumire()).isEqualTo(UPDATED_DENUMIRE);
        assertThat(testClient.getCodFiscal()).isEqualTo(UPDATED_COD_FISCAL);
        assertThat(testClient.getNumarRegistruComert()).isEqualTo(UPDATED_NUMAR_REGISTRU_COMERT);
        assertThat(testClient.getAdresaPunctLucru()).isEqualTo(UPDATED_ADRESA_PUNCT_LUCRU);
        assertThat(testClient.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getPersoanaContact()).isEqualTo(UPDATED_PERSOANA_CONTACT);
        assertThat(testClient.getContract()).isEqualTo(UPDATED_CONTRACT);
        assertThat(testClient.getDeratizare()).isEqualTo(UPDATED_DERATIZARE);
        assertThat(testClient.getDezinsectie()).isEqualTo(UPDATED_DEZINSECTIE);
        assertThat(testClient.getDezinfectie()).isEqualTo(UPDATED_DEZINFECTIE);
        assertThat(testClient.getFrecventaDeratizare()).isEqualTo(UPDATED_FRECVENTA_DERATIZARE);
        assertThat(testClient.getFrecventaDezinsectie()).isEqualTo(UPDATED_FRECVENTA_DEZINSECTIE);
        assertThat(testClient.getFrecventaDezinfectie()).isEqualTo(UPDATED_FRECVENTA_DEZINFECTIE);
    }

    @Test
    @Transactional
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, client.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(client))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();
        client.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
