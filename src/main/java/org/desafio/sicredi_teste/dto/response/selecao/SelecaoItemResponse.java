package org.desafio.sicredi_teste.dto.response.selecao;

import java.util.Map;

public record SelecaoItemResponse(
        String texto,
        String url,
        Map<String, Object> body
) {}
