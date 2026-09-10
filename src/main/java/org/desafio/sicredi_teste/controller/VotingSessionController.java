package org.desafio.sicredi_teste.controller;

import jakarta.validation.Valid;
import org.desafio.sicredi_teste.dto.request.VotingSessionRequest;
import org.desafio.sicredi_teste.dto.response.VotingResultResponse;
import org.desafio.sicredi_teste.dto.response.VotingSessionResponse;
import org.desafio.sicredi_teste.entity.VotingSession;
import org.desafio.sicredi_teste.usecase.GetVotingResultUseCase;
import org.desafio.sicredi_teste.usecase.OpenVotingSessionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/voting-sessions")
public class VotingSessionController {

    private static final Logger log = LoggerFactory.getLogger(VotingSessionController.class);

    private final OpenVotingSessionUseCase openVotingSessionUseCase;
    private final GetVotingResultUseCase getVotingResultUseCase;

    public VotingSessionController(OpenVotingSessionUseCase openVotingSessionUseCase, GetVotingResultUseCase getVotingResultUseCase) {
        this.openVotingSessionUseCase = openVotingSessionUseCase;
        this.getVotingResultUseCase = getVotingResultUseCase;
    }

    @PostMapping
    public ResponseEntity<VotingSessionResponse> create(@Valid @RequestBody VotingSessionRequest request){
        log.info("openVotingSession INIT agendaId={} durationInMinutes={}", request.agendaId(), request.durationInMinutes());
        VotingSessionResponse response = openVotingSessionUseCase.execute(request);
        log.info("openVotingSession FINISH agendaId={} durationInMinutes={}", request.agendaId(), request.durationInMinutes());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/result")
    public ResponseEntity<VotingResultResponse> getVotingResult(@RequestParam Long agendaId) {
        log.info("getVotingResult INIT agendaId={}", agendaId);
        VotingResultResponse response = getVotingResultUseCase.execute(agendaId);
        log.info("getVotingResult FINISH agendaId={} yesVotes={} noVotes={}", response.agendaId(), response.yesVotes(), response.noVotes());
        return ResponseEntity.ok(response);
    }
}
