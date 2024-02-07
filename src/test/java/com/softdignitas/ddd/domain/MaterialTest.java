package com.softdignitas.ddd.domain;

import static com.softdignitas.ddd.domain.CompanieTestSamples.*;
import static com.softdignitas.ddd.domain.JTMaterialProcesVerbalTestSamples.*;
import static com.softdignitas.ddd.domain.MaterialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.softdignitas.ddd.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MaterialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Material.class);
        Material material1 = getMaterialSample1();
        Material material2 = new Material();
        assertThat(material1).isNotEqualTo(material2);

        material2.setId(material1.getId());
        assertThat(material1).isEqualTo(material2);

        material2 = getMaterialSample2();
        assertThat(material1).isNotEqualTo(material2);
    }

    @Test
    void jTMaterialProcesVerbalTest() throws Exception {
        Material material = getMaterialRandomSampleGenerator();
        JTMaterialProcesVerbal jTMaterialProcesVerbalBack = getJTMaterialProcesVerbalRandomSampleGenerator();

        material.addJTMaterialProcesVerbal(jTMaterialProcesVerbalBack);
        assertThat(material.getJTMaterialProcesVerbals()).containsOnly(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProdus()).isEqualTo(material);

        material.removeJTMaterialProcesVerbal(jTMaterialProcesVerbalBack);
        assertThat(material.getJTMaterialProcesVerbals()).doesNotContain(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProdus()).isNull();

        material.jTMaterialProcesVerbals(new HashSet<>(Set.of(jTMaterialProcesVerbalBack)));
        assertThat(material.getJTMaterialProcesVerbals()).containsOnly(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProdus()).isEqualTo(material);

        material.setJTMaterialProcesVerbals(new HashSet<>());
        assertThat(material.getJTMaterialProcesVerbals()).doesNotContain(jTMaterialProcesVerbalBack);
        assertThat(jTMaterialProcesVerbalBack.getProdus()).isNull();
    }

    @Test
    void companieTest() throws Exception {
        Material material = getMaterialRandomSampleGenerator();
        Companie companieBack = getCompanieRandomSampleGenerator();

        material.setCompanie(companieBack);
        assertThat(material.getCompanie()).isEqualTo(companieBack);

        material.companie(null);
        assertThat(material.getCompanie()).isNull();
    }
}
