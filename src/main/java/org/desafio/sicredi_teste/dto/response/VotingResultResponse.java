package org.desafio.sicredi_teste.dto.response;

public record VotingResultResponse(
        Long agendaId,
        Long yesVotes,
        Long noVotes
) {
}
