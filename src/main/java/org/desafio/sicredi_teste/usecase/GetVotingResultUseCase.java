package org.desafio.sicredi_teste.usecase;

import org.desafio.sicredi_teste.dto.response.VotingResultResponse;

public interface GetVotingResultUseCase {
    VotingResultResponse execute(Long agendaId);
}
