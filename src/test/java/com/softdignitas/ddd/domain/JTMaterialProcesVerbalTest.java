package com.softdignitas.ddd.domain;

import static com.softdignitas.ddd.domain.JTMaterialProcesVerbalTestSamples.*;
import static com.softdignitas.ddd.domain.MaterialTestSamples.*;
import static com.softdignitas.ddd.domain.ProcesVerbalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.softdignitas.ddd.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JTMaterialProcesVerbalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JTMaterialProcesVerbal.class);
        JTMaterialProcesVerbal jTMaterialProcesVerbal1 = getJTMaterialProcesVerbalSample1();
        JTMaterialProcesVerbal jTMaterialProcesVerbal2 = new JTMaterialProcesVerbal();
        assertThat(jTMaterialProcesVerbal1).isNotEqualTo(jTMaterialProcesVerbal2);

        jTMaterialProcesVerbal2.setId(jTMaterialProcesVerbal1.getId());
        assertThat(jTMaterialProcesVerbal1).isEqualTo(jTMaterialProcesVerbal2);

        jTMaterialProcesVerbal2 = getJTMaterialProcesVerbalSample2();
        assertThat(jTMaterialProcesVerbal1).isNotEqualTo(jTMaterialProcesVerbal2);
    }

    @Test
    void produsTest() throws Exception {
        JTMaterialProcesVerbal jTMaterialProcesVerbal = getJTMaterialProcesVerbalRandomSampleGenerator();
        Material materialBack = getMaterialRandomSampleGenerator();

        jTMaterialProcesVerbal.setProdus(materialBack);
        assertThat(jTMaterialProcesVerbal.getProdus()).isEqualTo(materialBack);

        jTMaterialProcesVerbal.produs(null);
        assertThat(jTMaterialProcesVerbal.getProdus()).isNull();
    }

    @Test
    void procesVerbalTest() throws Exception {
        JTMaterialProcesVerbal jTMaterialProcesVerbal = getJTMaterialProcesVerbalRandomSampleGenerator();
        ProcesVerbal procesVerbalBack = getProcesVerbalRandomSampleGenerator();

        jTMaterialProcesVerbal.setProcesVerbal(procesVerbalBack);
        assertThat(jTMaterialProcesVerbal.getProcesVerbal()).isEqualTo(procesVerbalBack);

        jTMaterialProcesVerbal.procesVerbal(null);
        assertThat(jTMaterialProcesVerbal.getProcesVerbal()).isNull();
    }
}
