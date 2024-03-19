package com.softdignitas.ddd.service.dto;

import com.softdignitas.ddd.domain.enumeration.Procedura;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * A DTO representing a ProcesVerbalDTO, with only the public attributes.
 */
public record MaterialDTO(
    @Enumerated(EnumType.STRING) Procedura procedura,
    @NotNull String factura,
    @NotNull String denumire,

    @NotNull String lot,
    LocalDate dataAchizitionare,
    LocalDate dataExpirare,
    String dilutie,
    Integer timpContact,
    String metodaAplicare,
    Integer gramaj,
    Integer cantitate
) {}
