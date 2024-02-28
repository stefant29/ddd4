package com.softdignitas.ddd.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdignitas.ddd.domain.Companie;
import com.softdignitas.ddd.repository.DDDRepository;
import com.softdignitas.ddd.repository.UserRepository;
import com.softdignitas.ddd.repository.UtilizatorRepository;
import com.softdignitas.ddd.security.SecurityUtils;
import com.softdignitas.ddd.web.rest.errors.RecordNotFoundException;
import com.softdignitas.ddd.web.rest.lazyload.FilterMetadata;
import com.softdignitas.ddd.web.rest.lazyload.TableLazyLoadEvent;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DDDEntitateResource<T> {

    private final Logger log = LoggerFactory.getLogger(DDDEntitateResource.class);

    private final ObjectMapper objectMapper;

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private UserRepository userRepository;

    DDDRepository repository;

    public DDDEntitateResource() {
        this.objectMapper = new ObjectMapper();
    }

    private Specification<T> filterByCompanie(Specification<T> specification) {
        final Companie companie = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .flatMap(utilizatorRepository::findOneByUser)
            .orElseThrow(() -> new RecordNotFoundException("UTILIZATOR", ""))
            .getCompanie();

        return specification.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("companie"), companie));
    }

    @GetMapping("")
    public Page<T> getAll(TableLazyLoadEvent tableLazyLoadEvent) throws JsonProcessingException {
        Map filters = objectMapper.readValue(tableLazyLoadEvent.getFilters(), Map.class);
        Specification<T> filtersSpecification = filterByCompanie(getSpecification(filters));

        return repository.findAll(filtersSpecification, getPageable(tableLazyLoadEvent));
    }

    private Pageable getPageable(TableLazyLoadEvent tableLazyLoadEvent) {
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

        return PageRequest.of(page, size, sort);
    }

    private Specification<T> getSpecification(Map<String, LinkedHashMap<String, String>> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, LinkedHashMap<String, String>> entry : filters.entrySet()) {
                String fieldName = entry.getKey();
                FilterMetadata filter = new FilterMetadata(entry.getValue());
                String value = filter.getValue();
                String matchMode = filter.getMatchMode();

                if (value == null || value.isEmpty()) {
                    log.debug("Value for {} is null or empty", fieldName);
                    continue;
                }

                switch (matchMode.toLowerCase()) {
                    case "equals":
                        predicates.add(criteriaBuilder.equal(root.get(fieldName), value));
                        break;
                    case "startswith":
                        predicates.add(criteriaBuilder.like(root.get(fieldName), value + "%"));
                        break;
                    case "contains":
                        predicates.add(criteriaBuilder.like(root.get(fieldName), "%" + value + "%"));
                        break;
                    case "notcontains":
                        predicates.add(criteriaBuilder.notLike(root.get(fieldName), "%" + value + "%"));
                        break;
                    case "endswith":
                        predicates.add(criteriaBuilder.like(root.get(fieldName), "%" + value));
                        break;
                    case "notequals":
                        predicates.add(criteriaBuilder.notEqual(root.get(fieldName), value));
                        break;
                    case "lt":
                        predicates.add(criteriaBuilder.lessThan(root.get(fieldName), value));
                        break;
                    case "lte":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), value));
                        break;
                    case "gt":
                        predicates.add(criteriaBuilder.greaterThan(root.get(fieldName), value));
                        break;
                    case "gte":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), value));
                        break;
                    default:
                        log.debug("Match mode {} not supported", matchMode);
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
