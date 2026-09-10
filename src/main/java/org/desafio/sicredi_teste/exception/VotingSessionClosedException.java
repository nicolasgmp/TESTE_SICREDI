package org.desafio.sicredi_teste.exception;

public class VotingSessionClosedException extends RuntimeException{
    public VotingSessionClosedException(String message) {
        super(message);
    }
}
