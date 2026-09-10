package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.request.CastVoteRequest;
import org.desafio.sicredi_teste.entity.Agenda;
import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.entity.enums.VoteType;
import org.desafio.sicredi_teste.exception.AgendaNotFoundException;
import org.desafio.sicredi_teste.exception.DuplicateVoteException;
import org.desafio.sicredi_teste.exception.VotingSessionClosedException;
import org.desafio.sicredi_teste.repository.VoteRepository;
import org.desafio.sicredi_teste.repository.VotingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CastVoteUseCaseImplTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VotingSessionRepository votingSessionRepository;

    private CastVoteUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CastVoteUseCaseImpl(
                voteRepository,
                votingSessionRepository
        );
    }

    @Test
    void shouldCastVote() {
        Long agendaId = 1L;
        Long associateId = 10L;

        Agenda agenda = new Agenda("Pauta de teste");
        VotingSession session = mock(VotingSession.class);

        CastVoteRequest request =
                new CastVoteRequest(agendaId, associateId, VoteType.YES);

        when(votingSessionRepository.findByAgendaId(agendaId))
                .thenReturn(Optional.of(session));

        when(session.getClosedAt())
                .thenReturn(LocalDateTime.now().plusMinutes(1));

        when(voteRepository.existsByAgendaIdAndAssociateId(
                agendaId, associateId))
                .thenReturn(false);

        when(session.getAgenda())
                .thenReturn(agenda);

        useCase.execute(request);

        verify(voteRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAgendaDoesNotExist() {
        Long agendaId = 1L;

        CastVoteRequest request =
                new CastVoteRequest(agendaId, 10L, VoteType.YES);

        when(votingSessionRepository.findByAgendaId(agendaId))
                .thenReturn(Optional.empty());

        assertThrows(
                AgendaNotFoundException.class,
                () -> useCase.execute(request)
        );

        verify(voteRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenVotingSessionIsClosed() {
        Long agendaId = 1L;

        VotingSession session = mock(VotingSession.class);

        CastVoteRequest request =
                new CastVoteRequest(agendaId, 10L, VoteType.YES);

        when(votingSessionRepository.findByAgendaId(agendaId))
                .thenReturn(Optional.of(session));

        when(session.getClosedAt())
                .thenReturn(LocalDateTime.now().minusMinutes(1));

        assertThrows(
                VotingSessionClosedException.class,
                () -> useCase.execute(request)
        );

        verify(voteRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAssociateAlreadyVoted() {
        Long agendaId = 1L;
        Long associateId = 10L;

        VotingSession session = mock(VotingSession.class);

        CastVoteRequest request =
                new CastVoteRequest(agendaId, associateId, VoteType.YES);

        when(votingSessionRepository.findByAgendaId(agendaId))
                .thenReturn(Optional.of(session));

        when(session.getClosedAt())
                .thenReturn(LocalDateTime.now().plusMinutes(1));

        when(voteRepository.existsByAgendaIdAndAssociateId(
                agendaId, associateId))
                .thenReturn(true);

        assertThrows(
                DuplicateVoteException.class,
                () -> useCase.execute(request)
        );

        verify(voteRepository, never()).save(any());
    }
}
