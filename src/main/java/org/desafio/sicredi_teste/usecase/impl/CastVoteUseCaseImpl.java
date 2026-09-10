package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.request.CastVoteRequest;
import org.desafio.sicredi_teste.entity.Vote;
import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.exception.AgendaNotFoundException;
import org.desafio.sicredi_teste.exception.DuplicateVoteException;
import org.desafio.sicredi_teste.exception.VotingSessionClosedException;
import org.desafio.sicredi_teste.repository.VoteRepository;
import org.desafio.sicredi_teste.repository.VotingSessionRepository;
import org.desafio.sicredi_teste.usecase.CastVoteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CastVoteUseCaseImpl implements CastVoteUseCase {

    private static final Logger log = LoggerFactory.getLogger(CastVoteUseCaseImpl.class);

    private final VoteRepository voteRepository;
    private final VotingSessionRepository votingSessionRepository;

    public CastVoteUseCaseImpl(VoteRepository voteRepository, VotingSessionRepository votingSessionRepository) {
        this.voteRepository = voteRepository;
        this.votingSessionRepository = votingSessionRepository;
    }

    @Override
    public void execute(CastVoteRequest request) {
        log.info("castVoteUseCaseImpl | INIT | agendaId={} | associateId={} | type={}", request.agendaId(), request.associateId(), request.type());

        VotingSession session = votingSessionRepository.findByAgendaId(request.agendaId())
                .orElseThrow(() -> new AgendaNotFoundException("Esta pauta não existe"));

        if (session.getClosedAt().isBefore(LocalDateTime.now())) {
            throw new VotingSessionClosedException("Esta sessão de votação já foi encerrada");
        }

        if (voteRepository.existsByAgendaIdAndAssociateId(request.agendaId(), request.associateId())) {
            throw new DuplicateVoteException("Este associado já votou nesta sessão");
        }

        voteRepository.save(new Vote(session.getAgenda(), request.associateId(), request.type()));

        log.info("castVoteUseCaseImpl | FINISH | agendaId={} | associateId={} | type={}", request.agendaId(), request.associateId(), request.type());
    }
}
