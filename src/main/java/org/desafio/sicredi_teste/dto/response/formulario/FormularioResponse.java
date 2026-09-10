package org.desafio.sicredi_teste.dto.response.formulario;

import org.desafio.sicredi_teste.dto.response.ButtonResponse;

import java.util.List;

public record FormularioResponse(
        String tipo,
        String titulo,
        List<FormularioItemResponse> itens,
        ButtonResponse botaoOk,
        ButtonResponse botaoCancelar
) {
}
