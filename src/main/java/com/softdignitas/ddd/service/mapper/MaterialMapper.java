package com.softdignitas.ddd.service.mapper;

import com.softdignitas.ddd.domain.Material;
import com.softdignitas.ddd.service.dto.MaterialDTO;
import org.springframework.beans.BeanUtils;

public class MaterialMapper {

    private MaterialMapper() {}

    public static Material toEntity(MaterialDTO dto) {
        Material entity = new Material();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
