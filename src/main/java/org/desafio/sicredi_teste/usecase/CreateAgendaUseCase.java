package org.desafio.sicredi_teste.usecase;

import org.desafio.sicredi_teste.dto.request.CreateAgendaRequest;
import org.desafio.sicredi_teste.entity.Agenda;

public interface CreateAgendaUseCase {
    Agenda execute(CreateAgendaRequest request);
}
