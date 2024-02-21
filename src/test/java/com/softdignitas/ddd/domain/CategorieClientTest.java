package com.softdignitas.ddd.domain;

import static com.softdignitas.ddd.domain.CategorieClientTestSamples.*;
import static com.softdignitas.ddd.domain.ClientTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.softdignitas.ddd.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategorieClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieClient.class);
        CategorieClient categorieClient1 = getCategorieClientSample1();
        CategorieClient categorieClient2 = new CategorieClient();
        assertThat(categorieClient1).isNotEqualTo(categorieClient2);

        categorieClient2.setId(categorieClient1.getId());
        assertThat(categorieClient1).isEqualTo(categorieClient2);

        categorieClient2 = getCategorieClientSample2();
        assertThat(categorieClient1).isNotEqualTo(categorieClient2);
    }

    @Test
    void clientTest() throws Exception {
        CategorieClient categorieClient = getCategorieClientRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        categorieClient.addClient(clientBack);
        assertThat(categorieClient.getClients()).containsOnly(clientBack);
        assertThat(clientBack.getCategorie()).isEqualTo(categorieClient);

        categorieClient.removeClient(clientBack);
        assertThat(categorieClient.getClients()).doesNotContain(clientBack);
        assertThat(clientBack.getCategorie()).isNull();

        categorieClient.clients(new HashSet<>(Set.of(clientBack)));
        assertThat(categorieClient.getClients()).containsOnly(clientBack);
        assertThat(clientBack.getCategorie()).isEqualTo(categorieClient);

        categorieClient.setClients(new HashSet<>());
        assertThat(categorieClient.getClients()).doesNotContain(clientBack);
        assertThat(clientBack.getCategorie()).isNull();
    }
}
