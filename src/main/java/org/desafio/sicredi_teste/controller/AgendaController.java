package org.desafio.sicredi_teste.controller;

import jakarta.validation.Valid;
import org.desafio.sicredi_teste.dto.request.CreateAgendaRequest;
import org.desafio.sicredi_teste.dto.response.CreateAgendaResponse;
import org.desafio.sicredi_teste.entity.Agenda;
import org.desafio.sicredi_teste.usecase.CreateAgendaUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/agenda")
public class AgendaController {

    private static final Logger log = LoggerFactory.getLogger(AgendaController.class);

    private final CreateAgendaUseCase createAgendaUseCase;

    public AgendaController(CreateAgendaUseCase createAgendaUseCase) {
        this.createAgendaUseCase = createAgendaUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateAgendaResponse> create(@Valid @RequestBody CreateAgendaRequest request) {
        log.info("createAgenda INIT title={}", request.title());
        Agenda agenda = createAgendaUseCase.execute(request);
        log.info("createAgenda FINISH title={}", request.title());

        return ResponseEntity.ok().body(new CreateAgendaResponse(agenda.getId(), agenda.getTitle()));
    }
}
