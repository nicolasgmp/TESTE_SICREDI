package org.desafio.sicredi_teste.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CreateAgendaRequest(
        @NotBlank(message = "A pauta precisa de um título")
        @JsonProperty("agenda_title")
        String title
) {
}
