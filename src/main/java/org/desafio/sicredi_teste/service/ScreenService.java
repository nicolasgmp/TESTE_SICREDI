package org.desafio.sicredi_teste.service;

import org.desafio.sicredi_teste.dto.response.ButtonResponse;
import org.desafio.sicredi_teste.dto.response.formulario.FormularioItemResponse;
import org.desafio.sicredi_teste.dto.response.formulario.FormularioResponse;
import org.desafio.sicredi_teste.dto.response.selecao.SelecaoItemResponse;
import org.desafio.sicredi_teste.dto.response.selecao.SelecaoResponse;
import org.desafio.sicredi_teste.entity.enums.VoteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScreenService {

    private static final Logger log = LoggerFactory.getLogger(ScreenService.class);

    @Value("${app.sicredi.url}")
    private String baseUrl;

    public FormularioResponse createAgendaScreen() {
        log.info("createAgendaScreen INIT");
        FormularioItemResponse titleItem = new FormularioItemResponse(
                "INPUT_TEXTO",
                "title",
                "Título da pauta",
                ""
        );
        ButtonResponse createButton = new ButtonResponse(
                "Criar Pauta",
                baseUrl + "/v1/agenda",
                Map.of()
        );
        ButtonResponse cancelButton = new ButtonResponse(
                "Cancelar",
                baseUrl,
                null
        );

        FormularioResponse response = new FormularioResponse(
                "FORMULARIO",
                "Criação de Pauta",
                List.of(titleItem),
                createButton,
                cancelButton
        );
        log.info("createAgendaScreen FINISH tipo={} titulo={}", response.tipo(), response.titulo());
        return response;
    }

    public FormularioResponse openVotingSessionScreen(Long agendaId) {
        log.info("openVotingSessionScreen INIT agendaId={}", agendaId);
        FormularioItemResponse durationItem = new FormularioItemResponse(
                "INPUT_NUMERO",
                "durationInMinutes",
                "Duração da sessão (minutos)",
                "1"
        );
        ButtonResponse openButton = new ButtonResponse(
                "Abrir sessão",
                baseUrl + "/v1/voting-session",
                Map.of("idAgenda", agendaId)
        );
        ButtonResponse cancelButton = new ButtonResponse(
                "Cancelar",
                baseUrl,
                null
        );

        FormularioResponse response = new FormularioResponse(
                "FORMULARIO",
                "Abertura de Sessão de Votação",
                List.of(durationItem),
                openButton,
                cancelButton
        );
        log.info("openVotingSessionScreen FINISH agendaId={} tipo={} titulo={}", agendaId, response.tipo(), response.titulo());
        return response;
    }

    public SelecaoResponse createVoteScreen(Long agendaId, Long associateId) {
        log.info("createVoteScreen INIT agendaId={} associateId={}", agendaId, associateId);
        SelecaoItemResponse yesOption = new SelecaoItemResponse(
                "SIM",
                baseUrl + "/v1/vote",
                Map.of(
                        "agendaId", agendaId,
                        "associateId", associateId,
                        "type", VoteType.YES
                ));
        SelecaoItemResponse noOption = new SelecaoItemResponse(
                "NÃO",
                baseUrl + "/v1/vote",
                Map.of(
                        "agendaId", agendaId,
                        "associateId", associateId,
                        "type", VoteType.NO
                ));

        SelecaoResponse response = new SelecaoResponse(
                "SELECAO",
                "Votação",
                List.of(yesOption, noOption)
        );
        log.info("createVoteScreen FINISH agendaId={} associateId={} tipo={} titulo={}", agendaId, associateId, response.tipo(), response.titulo());
        return response;
    }
}
