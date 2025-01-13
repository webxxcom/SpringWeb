package com.web.warehouse.sys.db.mysql;

public class InvalidConnectionException extends RuntimeException{
    public InvalidConnectionException(String message, Exception ex){
        super(message, ex);
    }

    public InvalidConnectionException(Exception ex){
        super(ex);
    }

    public InvalidConnectionException(String message){
        super(message);
    }
}
