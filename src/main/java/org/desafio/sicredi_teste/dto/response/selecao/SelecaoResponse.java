package org.desafio.sicredi_teste.dto.response.selecao;

import java.util.List;

public record SelecaoResponse(
        String tipo,
        String titulo,
        List<SelecaoItemResponse> itens
) {}
