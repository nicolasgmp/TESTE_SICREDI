package org.desafio.sicredi_teste.exception;

public class VotingSessionStillOpenException extends RuntimeException{
    public VotingSessionStillOpenException(String message) {
        super(message);
    }
}
