package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.request.VotingSessionRequest;
import org.desafio.sicredi_teste.dto.response.VotingSessionResponse;
import org.desafio.sicredi_teste.entity.Agenda;
import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.exception.AgendaNotFoundException;
import org.desafio.sicredi_teste.repository.AgendaRepository;
import org.desafio.sicredi_teste.repository.VotingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenVotingSessionUseCaseImplTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private AgendaRepository agendaRepository;

    private OpenVotingSessionUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new OpenVotingSessionUseCaseImpl(
                votingSessionRepository,
                agendaRepository
        );
    }

    @Test
    void shouldOpenVotingSessionAndReturnEndTime() {
        Long agendaId = 1L;

        Agenda agenda = new Agenda("Pauta de teste");

        VotingSessionRequest request =
                new VotingSessionRequest(agendaId, 5L);

        when(agendaRepository.findById(agendaId))
                .thenReturn(Optional.of(agenda));

        LocalDateTime before = LocalDateTime.now();

        VotingSessionResponse response = useCase.execute(request);

        LocalDateTime after = LocalDateTime.now();

        verify(votingSessionRepository).save(any(VotingSession.class));

        assertTrue(response.endTime().isAfter(before.plusMinutes(5).minusSeconds(1)));
        assertTrue(response.endTime().isBefore(after.plusMinutes(5).plusSeconds(1)));
    }

    @Test
    void shouldThrowExceptionWhenAgendaDoesNotExist() {
        Long agendaId = 1L;

        VotingSessionRequest request =
                new VotingSessionRequest(agendaId, 5L);

        when(agendaRepository.findById(agendaId))
                .thenReturn(Optional.empty());

        assertThrows(
                AgendaNotFoundException.class,
                () -> useCase.execute(request)
        );

        verify(votingSessionRepository, never())
                .save(any(VotingSession.class));
    }
}
