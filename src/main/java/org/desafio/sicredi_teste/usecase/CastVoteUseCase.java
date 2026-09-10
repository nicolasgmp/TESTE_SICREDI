package org.desafio.sicredi_teste.usecase;

import org.desafio.sicredi_teste.dto.request.CastVoteRequest;

public interface CastVoteUseCase {
    void execute(CastVoteRequest request);
}
