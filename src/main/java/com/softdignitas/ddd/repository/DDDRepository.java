package com.softdignitas.ddd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DDDRepository<ENTITY, KEY> extends JpaRepository<ENTITY, KEY>, JpaSpecificationExecutor<ENTITY> {
    Page<ENTITY> findAll(Specification spec, Pageable pageable);
}
