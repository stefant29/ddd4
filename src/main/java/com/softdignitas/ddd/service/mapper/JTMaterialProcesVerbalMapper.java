package com.softdignitas.ddd.service.mapper;

import com.softdignitas.ddd.domain.JTMaterialProcesVerbal;
import org.springframework.beans.BeanUtils;

public class JTMaterialProcesVerbalMapper {

    private JTMaterialProcesVerbalMapper() {}

    public static JTMaterialProcesVerbal toEntity(JTMaterialProcesVerbal dto) {
        JTMaterialProcesVerbal entity = new JTMaterialProcesVerbal();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
