package com.softdignitas.ddd.domain;

import static com.softdignitas.ddd.domain.ClientTestSamples.*;
import static com.softdignitas.ddd.domain.CompanieTestSamples.*;
import static com.softdignitas.ddd.domain.JTMaterialProcesVerbalTestSamples.*;
import static com.softdignitas.ddd.domain.ProcesVerbalTestSamples.*;
import static com.softdignitas.ddd.domain.UtilizatorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.softdignitas.ddd.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProcesVerbalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcesVerbal.class);
        ProcesVerbal procesVerbal1 = getProcesVerbalSample1();
        ProcesVerbal procesVerbal2 = new ProcesVerbal();
        assertThat(procesVerbal1).isNotEqualTo(procesVerbal2);

        procesVerbal2.setId(procesVerbal1.getId());
        assertThat(procesVerbal1).isEqualTo(procesVerbal2);

        procesVerbal2 = getProcesVerbalSample2();
        assertThat(procesVerbal1).isNotEqualTo(procesVerbal2);
    }

    @Test
    void jTMaterialProcesVerbalTest() throws Exception {
        ProcesVerbal procesVerbal = getProcesVerbalRandomSampleGenerator();
        JTMaterialProcesVerbal jTMaterialProcesVerbalBack = getJTMaterialProcesVerbalRandomSampleGenerator();

        procesVerbal.addJTMaterialProcesVerbal(jTMaterialProcesVerbalBack);
        assertThat(procesVerbal.getJTMaterialProcesVerbals()).containsOnly(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProcesVerbal()).isEqualTo(procesVerbal);

        procesVerbal.removeJTMaterialProcesVerbal(jTMaterialProcesVerbalBack);
        assertThat(procesVerbal.getJTMaterialProcesVerbals()).doesNotContain(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProcesVerbal()).isNull();

        procesVerbal.jTMaterialProcesVerbals(new HashSet<>(Set.of(jTMaterialProcesVerbalBack)));
        assertThat(procesVerbal.getJTMaterialProcesVerbals()).containsOnly(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProcesVerbal()).isEqualTo(procesVerbal);

        procesVerbal.setJTMaterialProcesVerbals(new HashSet<>());
        assertThat(procesVerbal.getJTMaterialProcesVerbals()).doesNotContain(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProcesVerbal()).isNull();
    }

    @Test
    void companieTest() throws Exception {
        ProcesVerbal procesVerbal = getProcesVerbalRandomSampleGenerator();
        Companie companieBack = getCompanieRandomSampleGenerator();

        procesVerbal.setCompanie(companieBack);
        assertThat(procesVerbal.getCompanie()).isEqualTo(companieBack);

        procesVerbal.companie(null);
        assertThat(procesVerbal.getCompanie()).isNull();
    }

    @Test
    void clientTest() throws Exception {
        ProcesVerbal procesVerbal = getProcesVerbalRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        procesVerbal.setClient(clientBack);
        assertThat(procesVerbal.getClient()).isEqualTo(clientBack);

        procesVerbal.client(null);
        assertThat(procesVerbal.getClient()).isNull();
    }

    @Test
    void operatorTest() throws Exception {
        ProcesVerbal procesVerbal = getProcesVerbalRandomSampleGenerator();
        Utilizator utilizatorBack = getUtilizatorRandomSampleGenerator();

        procesVerbal.setOperator(utilizatorBack);
        assertThat(procesVerbal.getOperator()).isEqualTo(utilizatorBack);

        procesVerbal.operator(null);
        assertThat(procesVerbal.getOperator()).isNull();
    }
}
