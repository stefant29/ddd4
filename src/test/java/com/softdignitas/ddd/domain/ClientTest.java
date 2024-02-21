package com.softdignitas.ddd.domain;

import static com.softdignitas.ddd.domain.CategorieClientTestSamples.*;
import static com.softdignitas.ddd.domain.ClientTestSamples.*;
import static com.softdignitas.ddd.domain.CompanieTestSamples.*;
import static com.softdignitas.ddd.domain.ProcesVerbalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.softdignitas.ddd.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void proceseVerbaleTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        ProcesVerbal procesVerbalBack = getProcesVerbalRandomSampleGenerator();

        client.addProceseVerbale(procesVerbalBack);
        assertThat(client.getProceseVerbales()).containsOnly(procesVerbalBack);
        assertThat(procesVerbalBack.getClient()).isEqualTo(client);

        client.removeProceseVerbale(procesVerbalBack);
        assertThat(client.getProceseVerbales()).doesNotContain(procesVerbalBack);
        assertThat(procesVerbalBack.getClient()).isNull();

        client.proceseVerbales(new HashSet<>(Set.of(procesVerbalBack)));
        assertThat(client.getProceseVerbales()).containsOnly(procesVerbalBack);
        assertThat(procesVerbalBack.getClient()).isEqualTo(client);

        client.setProceseVerbales(new HashSet<>());
        assertThat(client.getProceseVerbales()).doesNotContain(procesVerbalBack);
        assertThat(procesVerbalBack.getClient()).isNull();
    }

    @Test
    void companieTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Companie companieBack = getCompanieRandomSampleGenerator();

        client.setCompanie(companieBack);
        assertThat(client.getCompanie()).isEqualTo(companieBack);

        client.companie(null);
        assertThat(client.getCompanie()).isNull();
    }

    @Test
    void categorieTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        CategorieClient categorieClientBack = getCategorieClientRandomSampleGenerator();

        client.setCategorie(categorieClientBack);
        assertThat(client.getCategorie()).isEqualTo(categorieClientBack);

        client.categorie(null);
        assertThat(client.getCategorie()).isNull();
    }
}
