package ua.nure.st.kpp.example.demo.dao;

public class OperationWasNotPerformedException extends DBException{

    public OperationWasNotPerformedException(String message) {
        super(message);
    }

    public OperationWasNotPerformedException(String message, Exception ex) {
        super(message, ex);
    }

    public OperationWasNotPerformedException(Exception ex) {
        super(ex);
    }
}
