package org.desafio.sicredi_teste.usecase;

import org.desafio.sicredi_teste.dto.request.VotingSessionRequest;
import org.desafio.sicredi_teste.dto.response.VotingSessionResponse;

public interface OpenVotingSessionUseCase {
    VotingSessionResponse execute(VotingSessionRequest request);
}
