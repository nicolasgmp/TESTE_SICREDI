package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.request.CreateAgendaRequest;
import org.desafio.sicredi_teste.entity.Agenda;
import org.desafio.sicredi_teste.repository.AgendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateAgendaUseCaseImplTest {

    @Mock
    private AgendaRepository agendaRepository;

    private CreateAgendaUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateAgendaUseCaseImpl(agendaRepository);
    }

    @Test
    void shouldCreateAgenda() {
        CreateAgendaRequest request =
                new CreateAgendaRequest("Pauta de teste");

        useCase.execute(request);

        verify(agendaRepository).save(any(Agenda.class));
    }
}
