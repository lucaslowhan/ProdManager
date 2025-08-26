package dev.lucaslowhan.prodmanager.infra.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
