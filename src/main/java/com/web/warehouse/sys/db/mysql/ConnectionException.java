package com.web.warehouse.sys.db.mysql;

public class ConnectionException extends RuntimeException{
    public ConnectionException(String message, Exception ex){
        super(message, ex);
    }

    public ConnectionException(Exception ex){
        super(ex);
    }

    public ConnectionException(String message){
        super(message);
    }
}
