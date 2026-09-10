package org.desafio.sicredi_teste.controller;

import org.desafio.sicredi_teste.dto.response.formulario.FormularioResponse;
import org.desafio.sicredi_teste.dto.response.selecao.SelecaoResponse;
import org.desafio.sicredi_teste.service.ScreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/screens")
public class ScreenController {

    private static final Logger log = LoggerFactory.getLogger(ScreenController.class);

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping("/create-agenda")
    public FormularioResponse createAgendaScreen(){
        log.info("createAgendaScreen INIT");
        FormularioResponse response = screenService.createAgendaScreen();
        log.info("createAgendaScreen FINISH tipo={} titulo={}", response.tipo(), response.titulo());
        return response;
    }

    @GetMapping("/open-voting-session")
    public FormularioResponse openVotingSessionScreen(@RequestParam Long agendaId){
        log.info("openVotingSessionScreen INIT agendaId={}", agendaId);
        FormularioResponse response = screenService.openVotingSessionScreen(agendaId);
        log.info("openVotingSessionScreen FINISH agendaId={} tipo={} titulo={}", agendaId, response.tipo(), response.titulo());
        return response;
    }

    @GetMapping("/create-vote")
    public SelecaoResponse createVoteScreen(@RequestParam Long agendaId, @RequestParam Long associateId){
        log.info("createVoteScreen INIT agendaId={} associateId={}", agendaId, associateId);
        SelecaoResponse response = screenService.createVoteScreen(agendaId, associateId);
        log.info("createVoteScreen FINISH agendaId={} associateId={} tipo={} titulo={}", agendaId, associateId, response.tipo(), response.titulo());
        return response;
    }
}
