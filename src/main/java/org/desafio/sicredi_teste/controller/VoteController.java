package org.desafio.sicredi_teste.controller;

import jakarta.validation.Valid;
import org.desafio.sicredi_teste.dto.request.CastVoteRequest;
import org.desafio.sicredi_teste.usecase.CastVoteUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/vote")
public class VoteController {

    private static final Logger log = LoggerFactory.getLogger(VoteController.class);

    private final CastVoteUseCase castVoteUseCase;

    public VoteController(CastVoteUseCase castVoteUseCase) {
        this.castVoteUseCase = castVoteUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CastVoteRequest request) {
        log.info("castVote INIT agendaId={} associateId={} type={}", request.agendaId(), request.associateId(), request.type());
        castVoteUseCase.execute(request);
        log.info("castVote FINISH agendaId={} associateId={} type={}", request.agendaId(), request.associateId(), request.type());
        return ResponseEntity.ok().build();
    }
}
