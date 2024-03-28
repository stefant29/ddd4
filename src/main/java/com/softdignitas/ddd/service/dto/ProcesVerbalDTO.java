package com.softdignitas.ddd.service.dto;

import com.softdignitas.ddd.domain.Client;
import com.softdignitas.ddd.domain.JTMaterialProcesVerbal;
import com.softdignitas.ddd.domain.Utilizator;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO representing a ProcesVerbalDTO, with only the public attributes.
 */
public record ProcesVerbalDTO(
    String id,
    LocalDate data,
    @NotNull(message = "Data not be null") Instant ora,
    Integer numarProcesVerbal,
    String reprezentant,
    String spatii,
    Integer suprafata,
    Integer rapelDezinsectie,
    Integer rapelDeratizare,
    Boolean garantieDezinsectie,
    Boolean garantieDeratizare,
    @NotNull(message = "Client may not be null") Client client,
    @NotNull(message = "Operator may not be null") Utilizator operator,

    Set<JTMaterialProcesVerbal> jTMaterialProcesVerbals
) {}
