package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.request.VotingSessionRequest;
import org.desafio.sicredi_teste.dto.response.VotingSessionResponse;
import org.desafio.sicredi_teste.entity.Agenda;
import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.exception.AgendaNotFoundException;
import org.desafio.sicredi_teste.repository.AgendaRepository;
import org.desafio.sicredi_teste.repository.VotingSessionRepository;
import org.desafio.sicredi_teste.usecase.OpenVotingSessionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OpenVotingSessionUseCaseImpl implements OpenVotingSessionUseCase {

    private static final Logger log = LoggerFactory.getLogger(OpenVotingSessionUseCaseImpl.class);

    private final VotingSessionRepository votingSessionRepository;
    private final AgendaRepository agendaRepository;

    public OpenVotingSessionUseCaseImpl(VotingSessionRepository votingSessionRepository, AgendaRepository agendaRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public VotingSessionResponse execute(VotingSessionRequest request) {
        log.info("openVotingSessionUseCaseImpl | INIT | agendaId={} | durationInMinutes={}", request.agendaId(), request.durationInMinutes());

        Agenda agenda = agendaRepository.findById(request.agendaId())
                .orElseThrow(() -> new AgendaNotFoundException("Esta pauta não existe"));
        LocalDateTime openedAt = LocalDateTime.now();
        LocalDateTime closedAt = openedAt.plusMinutes(request.durationInMinutes() != null ? request.durationInMinutes() : 1);

        VotingSession saved = votingSessionRepository.save(new VotingSession(agenda, openedAt, closedAt));

        log.info("openVotingSessionUseCase | FINISH | agendaId={} | closedAt={}", saved.getAgenda().getId(), saved.getClosedAt());

        return new VotingSessionResponse(saved.getClosedAt());
    }
}
