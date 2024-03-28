package com.softdignitas.ddd.service.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * A DTO representing a ProcesVerbalDTO, with only the public attributes.
 */
public record ProcesVerbalListDTO(
    String id,
    @NotNull(message = "Data not be null") Instant ora,
    Integer numarProcesVerbal,
    String reprezentant,
    String procedura,
    String produs,
    Integer cantitate,

    @NotNull(message = "Client may not be null") String client,
    @NotNull(message = "Operator may not be null") String operator
) {}
