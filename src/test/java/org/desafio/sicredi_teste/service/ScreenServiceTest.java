package org.desafio.sicredi_teste.service;

import org.desafio.sicredi_teste.dto.response.formulario.FormularioResponse;
import org.desafio.sicredi_teste.dto.response.selecao.SelecaoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScreenServiceTest {

    private ScreenService screenService;

    @BeforeEach
    void setUp() {
        screenService = new ScreenService();

        ReflectionTestUtils.setField(
                screenService,
                "baseUrl",
                "http://localhost:8080"
        );
    }

    @Test
    void shouldCreateAgendaScreen() {
        FormularioResponse response =
                screenService.createAgendaScreen();

        assertEquals("FORMULARIO", response.tipo());
        assertEquals("Criação de Pauta", response.titulo());
        assertEquals(1, response.itens().size());
        assertEquals("Criar Pauta", response.botaoOk().texto());
    }

    @Test
    void shouldCreateOpenVotingSessionScreen() {
        Long agendaId = 1L;

        FormularioResponse response =
                screenService.openVotingSessionScreen(agendaId);

        assertEquals("FORMULARIO", response.tipo());
        assertEquals("Abertura de Sessão de Votação", response.titulo());
        assertEquals(1, response.itens().size());
        assertEquals("Abrir sessão", response.botaoOk().texto());
    }

    @Test
    void shouldCreateVoteScreen() {
        Long agendaId = 1L;
        Long associateId = 10L;

        SelecaoResponse response =
                screenService.createVoteScreen(
                        agendaId,
                        associateId
                );

        assertEquals("SELECAO", response.tipo());
        assertEquals("Votação", response.titulo());
        assertEquals(2, response.itens().size());
    }
}
