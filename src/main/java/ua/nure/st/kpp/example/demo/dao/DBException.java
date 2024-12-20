package ua.nure.st.kpp.example.demo.dao;

public class DBException extends RuntimeException{
    public DBException(String message){
        super(message);
    }

    public DBException(String message, Exception ex){
        super(message, ex);
    }

    public DBException(Exception ex){
        super(ex);
    }
}
