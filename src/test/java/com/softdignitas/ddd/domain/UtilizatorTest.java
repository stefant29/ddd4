package com.softdignitas.ddd.domain;

import static com.softdignitas.ddd.domain.CompanieTestSamples.*;
import static com.softdignitas.ddd.domain.ProcesVerbalTestSamples.*;
import static com.softdignitas.ddd.domain.UtilizatorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.softdignitas.ddd.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UtilizatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilizator.class);
        Utilizator utilizator1 = getUtilizatorSample1();
        Utilizator utilizator2 = new Utilizator();
        assertThat(utilizator1).isNotEqualTo(utilizator2);

        utilizator2.setId(utilizator1.getId());
        assertThat(utilizator1).isEqualTo(utilizator2);

        utilizator2 = getUtilizatorSample2();
        assertThat(utilizator1).isNotEqualTo(utilizator2);
    }

    @Test
    void proceseVerbaleTest() throws Exception {
        Utilizator utilizator = getUtilizatorRandomSampleGenerator();
        ProcesVerbal procesVerbalBack = getProcesVerbalRandomSampleGenerator();

        utilizator.addProceseVerbale(procesVerbalBack);
        assertThat(utilizator.getProceseVerbales()).containsOnly(procesVerbalBack);
        assertThat(procesVerbalBack.getOperator()).isEqualTo(utilizator);

        utilizator.removeProceseVerbale(procesVerbalBack);
        assertThat(utilizator.getProceseVerbales()).doesNotContain(procesVerbalBack);
        assertThat(procesVerbalBack.getOperator()).isNull();

        utilizator.proceseVerbales(new HashSet<>(Set.of(procesVerbalBack)));
        assertThat(utilizator.getProceseVerbales()).containsOnly(procesVerbalBack);
        assertThat(procesVerbalBack.getOperator()).isEqualTo(utilizator);

        utilizator.setProceseVerbales(new HashSet<>());
        assertThat(utilizator.getProceseVerbales()).doesNotContain(procesVerbalBack);
        assertThat(procesVerbalBack.getOperator()).isNull();
    }

    @Test
    void companieTest() throws Exception {
        Utilizator utilizator = getUtilizatorRandomSampleGenerator();
        Companie companieBack = getCompanieRandomSampleGenerator();

        utilizator.setCompanie(companieBack);
        assertThat(utilizator.getCompanie()).isEqualTo(companieBack);

        utilizator.companie(null);
        assertThat(utilizator.getCompanie()).isNull();
    }
}
