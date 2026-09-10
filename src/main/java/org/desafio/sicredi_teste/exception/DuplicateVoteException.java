package org.desafio.sicredi_teste.exception;

public class DuplicateVoteException extends RuntimeException{
    public DuplicateVoteException(String message) {
        super(message);
    }
}
