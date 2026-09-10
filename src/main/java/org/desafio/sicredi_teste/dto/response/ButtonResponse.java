package org.desafio.sicredi_teste.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ButtonResponse(
        String texto,
        String url,
        Map<String, Object> body
) {}
