package com.softdignitas.ddd.domain;

import static com.softdignitas.ddd.domain.ClientTestSamples.*;
import static com.softdignitas.ddd.domain.CompanieTestSamples.*;
import static com.softdignitas.ddd.domain.MaterialTestSamples.*;
import static com.softdignitas.ddd.domain.ProcesVerbalTestSamples.*;
import static com.softdignitas.ddd.domain.UtilizatorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.softdignitas.ddd.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompanieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Companie.class);
        Companie companie1 = getCompanieSample1();
        Companie companie2 = new Companie();
        assertThat(companie1).isNotEqualTo(companie2);

        companie2.setId(companie1.getId());
        assertThat(companie1).isEqualTo(companie2);

        companie2 = getCompanieSample2();
        assertThat(companie1).isNotEqualTo(companie2);
    }

    @Test
    void utilizatoriTest() throws Exception {
        Companie companie = getCompanieRandomSampleGenerator();
        Utilizator utilizatorBack = getUtilizatorRandomSampleGenerator();

        companie.addUtilizatori(utilizatorBack);
        assertThat(companie.getUtilizatoris()).containsOnly(utilizatorBack);
        assertThat(utilizatorBack.getCompanie()).isEqualTo(companie);

        companie.removeUtilizatori(utilizatorBack);
        assertThat(companie.getUtilizatoris()).doesNotContain(utilizatorBack);
        assertThat(utilizatorBack.getCompanie()).isNull();

        companie.utilizatoris(new HashSet<>(Set.of(utilizatorBack)));
        assertThat(companie.getUtilizatoris()).containsOnly(utilizatorBack);
        assertThat(utilizatorBack.getCompanie()).isEqualTo(companie);

        companie.setUtilizatoris(new HashSet<>());
        assertThat(companie.getUtilizatoris()).doesNotContain(utilizatorBack);
        assertThat(utilizatorBack.getCompanie()).isNull();
    }

    @Test
    void clientiTest() throws Exception {
        Companie companie = getCompanieRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        companie.addClienti(clientBack);
        assertThat(companie.getClientis()).containsOnly(clientBack);
        assertThat(clientBack.getCompanie()).isEqualTo(companie);

        companie.removeClienti(clientBack);
        assertThat(companie.getClientis()).doesNotContain(clientBack);
        assertThat(clientBack.getCompanie()).isNull();

        companie.clientis(new HashSet<>(Set.of(clientBack)));
        assertThat(companie.getClientis()).containsOnly(clientBack);
        assertThat(clientBack.getCompanie()).isEqualTo(companie);

        companie.setClientis(new HashSet<>());
        assertThat(companie.getClientis()).doesNotContain(clientBack);
        assertThat(clientBack.getCompanie()).isNull();
    }

    @Test
    void materialeTest() throws Exception {
        Companie companie = getCompanieRandomSampleGenerator();
        Material materialBack = getMaterialRandomSampleGenerator();

        companie.addMateriale(materialBack);
        assertThat(companie.getMateriales()).containsOnly(materialBack);
        assertThat(materialBack.getCompanie()).isEqualTo(companie);

        companie.removeMateriale(materialBack);
        assertThat(companie.getMateriales()).doesNotContain(materialBack);
        assertThat(materialBack.getCompanie()).isNull();

        companie.materiales(new HashSet<>(Set.of(materialBack)));
        assertThat(companie.getMateriales()).containsOnly(materialBack);
        assertThat(materialBack.getCompanie()).isEqualTo(companie);

        companie.setMateriales(new HashSet<>());
        assertThat(companie.getMateriales()).doesNotContain(materialBack);
        assertThat(materialBack.getCompanie()).isNull();
    }

    @Test
    void proceseVerbaleTest() throws Exception {
        Companie companie = getCompanieRandomSampleGenerator();
        ProcesVerbal procesVerbalBack = getProcesVerbalRandomSampleGenerator();

        companie.addProceseVerbale(procesVerbalBack);
        assertThat(companie.getProceseVerbales()).containsOnly(procesVerbalBack);
        assertThat(procesVerbalBack.getCompanie()).isEqualTo(companie);

        companie.removeProceseVerbale(procesVerbalBack);
        assertThat(companie.getProceseVerbales()).doesNotContain(procesVerbalBack);
        assertThat(procesVerbalBack.getCompanie()).isNull();

        companie.proceseVerbales(new HashSet<>(Set.of(procesVerbalBack)));
        assertThat(companie.getProceseVerbales()).containsOnly(procesVerbalBack);
        assertThat(procesVerbalBack.getCompanie()).isEqualTo(companie);

        companie.setProceseVerbales(new HashSet<>());
        assertThat(companie.getProceseVerbales()).doesNotContain(procesVerbalBack);
        assertThat(procesVerbalBack.getCompanie()).isNull();
    }
}
