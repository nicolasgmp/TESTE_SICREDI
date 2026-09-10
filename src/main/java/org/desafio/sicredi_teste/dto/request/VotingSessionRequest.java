package org.desafio.sicredi_teste.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record VotingSessionRequest(
        @NotNull
        Long agendaId,
        Long durationInMinutes
) {
}
