package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.response.VotingResultResponse;
import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.entity.enums.VoteType;
import org.desafio.sicredi_teste.exception.AgendaNotFoundException;
import org.desafio.sicredi_teste.exception.VotingSessionStillOpenException;
import org.desafio.sicredi_teste.repository.VoteRepository;
import org.desafio.sicredi_teste.repository.VotingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetVotingResultUseCaseImplTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private VoteRepository voteRepository;

    private GetVotingResultUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetVotingResultUseCaseImpl(
                votingSessionRepository,
                voteRepository
        );
    }

    @Test
    void shouldReturnVotingResult() {
        Long agendaId = 1L;

        VotingSession session = mock(VotingSession.class);

        when(votingSessionRepository.findByAgendaId(agendaId))
                .thenReturn(Optional.of(session));

        when(session.getClosedAt())
                .thenReturn(LocalDateTime.now().minusMinutes(1));

        when(voteRepository.countByAgendaIdAndType(agendaId, VoteType.YES))
                .thenReturn(10L);

        when(voteRepository.countByAgendaIdAndType(agendaId, VoteType.NO))
                .thenReturn(5L);

        VotingResultResponse result = useCase.execute(agendaId);

        assertEquals(agendaId, result.agendaId());
        assertEquals(10L, result.yesVotes());
        assertEquals(5L, result.noVotes());
    }

    @Test
    void shouldThrowExceptionWhenSessionIsStillOpen() {
        Long agendaId = 1L;

        VotingSession session = mock(VotingSession.class);

        when(votingSessionRepository.findByAgendaId(agendaId))
                .thenReturn(Optional.of(session));

        when(session.getClosedAt())
                .thenReturn(LocalDateTime.now().plusMinutes(1));

        assertThrows(
                VotingSessionStillOpenException.class,
                () -> useCase.execute(agendaId)
        );
    }

    @Test
    void shouldThrowExceptionWhenSessionDoesNotExist() {
        Long agendaId = 1L;

        when(votingSessionRepository.findByAgendaId(agendaId))
                .thenReturn(Optional.empty());

        assertThrows(
                AgendaNotFoundException.class,
                () -> useCase.execute(agendaId)
        );
    }
}
