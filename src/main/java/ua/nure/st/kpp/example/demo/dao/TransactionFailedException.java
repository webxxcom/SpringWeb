package ua.nure.st.kpp.example.demo.dao;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String message){
        super(message);
    }

    public TransactionFailedException(String message, Exception ex){
        super(message,ex);
    }
}
