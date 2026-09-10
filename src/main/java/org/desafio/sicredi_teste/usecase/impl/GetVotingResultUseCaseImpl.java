package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.response.VotingResultResponse;
import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.entity.enums.VoteType;
import org.desafio.sicredi_teste.exception.AgendaNotFoundException;
import org.desafio.sicredi_teste.exception.VotingSessionStillOpenException;
import org.desafio.sicredi_teste.repository.VoteRepository;
import org.desafio.sicredi_teste.repository.VotingSessionRepository;
import org.desafio.sicredi_teste.usecase.GetVotingResultUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GetVotingResultUseCaseImpl implements GetVotingResultUseCase {
    private final VotingSessionRepository votingSessionRepository;
    private final VoteRepository voteRepository;

    private static final Logger log = LoggerFactory.getLogger(GetVotingResultUseCaseImpl.class);

    public GetVotingResultUseCaseImpl(VotingSessionRepository votingSessionRepository,  VoteRepository voteRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public VotingResultResponse execute(Long agendaId) {
        log.info("getVotingResultUseCaseImpl | INIT | agendaId={}", agendaId);


        VotingSession session = votingSessionRepository.findByAgendaId(agendaId).orElseThrow(() -> new AgendaNotFoundException("Esta pauta não está sendo votada"));
        if (session.getClosedAt().isAfter(LocalDateTime.now())) {
            throw new VotingSessionStillOpenException("A votação desta pauta não foi encerrada");
        }

        Long yesVotes = voteRepository.countByAgendaIdAndType(agendaId, VoteType.YES);
        Long noVotes = voteRepository.countByAgendaIdAndType(agendaId, VoteType.NO);

        log.info("getVotingResultUseCase FINISH agendaId={} yesVotes={} noVotes={}", agendaId, yesVotes, noVotes);

        return new VotingResultResponse(
               agendaId,
               yesVotes,
               noVotes
        );
    }
}
