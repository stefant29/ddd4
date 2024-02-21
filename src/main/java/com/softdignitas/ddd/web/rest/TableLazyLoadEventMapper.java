package com.softdignitas.ddd.web.rest;

import com.softdignitas.ddd.web.rest.lazyload.TableLazyLoadEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface TableLazyLoadEventMapper {
    //    @Mapping(target = "someProperty", ignore = true) // If there are properties to ignore during mapping
    //    @Mapping(source = "first", target = "first")
    //    @Mapping(source = "rows", target = "rows")
    //    @Mapping(source = "sortField", target = "sortField")
    //    @Mapping(source = "sortOrder", target = "sortOrder")
    //    TableLazyLoadEvent mapStringToTableLazyLoadEvent(String source);
}
