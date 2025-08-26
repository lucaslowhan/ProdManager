package dev.lucaslowhan.prodmanager.infra.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }

}
