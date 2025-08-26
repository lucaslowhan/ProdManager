package dev.lucaslowhan.prodmanager.infra.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);
    }
}
