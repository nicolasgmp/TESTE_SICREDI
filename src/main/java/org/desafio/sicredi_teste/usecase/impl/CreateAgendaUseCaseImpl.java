package org.desafio.sicredi_teste.usecase.impl;

import org.desafio.sicredi_teste.dto.request.CreateAgendaRequest;
import org.desafio.sicredi_teste.entity.Agenda;
import org.desafio.sicredi_teste.repository.AgendaRepository;
import org.desafio.sicredi_teste.usecase.CreateAgendaUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateAgendaUseCaseImpl implements CreateAgendaUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateAgendaUseCaseImpl.class);

    private final AgendaRepository agendaRepository;

    public CreateAgendaUseCaseImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public Agenda execute(CreateAgendaRequest request) {
        log.info("createAgendaUseCaseImpl | INIT | title={}", request.title());
        Agenda agenda = agendaRepository.save(new Agenda(request.title()));
        log.info("createAgendaUseCaseImpl | FINISH | title={}", request.title());
        return agenda;
    }
}
