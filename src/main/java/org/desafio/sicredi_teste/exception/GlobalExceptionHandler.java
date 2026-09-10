package org.desafio.sicredi_teste.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AgendaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAgendaNotFoundException(AgendaNotFoundException ex) {
        log.warn("handleAgendaNotFoundException ERROR message={}", ex.getMessage());
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(VotingSessionClosedException.class)
    public ResponseEntity<ErrorResponse> handleVotingSessionClosedException(VotingSessionClosedException ex) {
        log.warn("handleVotingSessionClosedException ERROR message={}", ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(VotingSessionStillOpenException.class)
    public ResponseEntity<ErrorResponse> handleVotingSessionStillOpenException(VotingSessionStillOpenException ex) {
        log.warn("handleVotingSessionStillOpenException ERROR message={}", ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateVoteException(DuplicateVoteException ex) {
        log.warn("handleDuplicateVoteException ERROR message={}", ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage()));
    }

    private record ErrorResponse(String message) {
    }
}
