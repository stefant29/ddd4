package com.softdignitas.ddd.web.rest;

import com.softdignitas.ddd.web.rest.lazyload.TableLazyLoadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DDDEntitateResource {

    private final Logger log = LoggerFactory.getLogger(DDDEntitateResource.class);

    JpaRepository<?, String> repository;

    @GetMapping("")
    public Page<?> getAll(TableLazyLoadEvent tableLazyLoadEvent) {
        log.debug("REST request to get all entities");

        int page = 0;
        int size = 10;

        if (tableLazyLoadEvent.getRows() != null) {
            size = tableLazyLoadEvent.getRows();
            if (tableLazyLoadEvent.getFirst() != null) {
                page = tableLazyLoadEvent.getFirst() / tableLazyLoadEvent.getRows();
            }
        }

        Sort sort = Sort.unsorted();
        if (tableLazyLoadEvent.getSortField() != null && tableLazyLoadEvent.getSortOrder() != null) {
            sort = Sort.by(tableLazyLoadEvent.getSortOrder().getSortDirection(), tableLazyLoadEvent.getSortField());
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return repository.findAll(pageRequest);
    }
}
