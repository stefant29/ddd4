package com.softdignitas.ddd.service.mapper;

import com.softdignitas.ddd.domain.ProcesVerbal;
import com.softdignitas.ddd.service.dto.ProcesVerbalDTO;
import org.springframework.beans.BeanUtils;

public class ProcesVerbalMapper {

    private ProcesVerbalMapper() {}

    public static ProcesVerbal toEntity(ProcesVerbalDTO dto) {
        ProcesVerbal entity = new ProcesVerbal();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
