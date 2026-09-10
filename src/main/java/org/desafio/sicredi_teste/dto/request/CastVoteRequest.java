package org.desafio.sicredi_teste.dto.request;

import jakarta.validation.constraints.NotNull;
import org.desafio.sicredi_teste.entity.enums.VoteType;

public record CastVoteRequest(
        @NotNull Long agendaId,
        @NotNull Long associateId,
        @NotNull VoteType type
) {
}
