package com.web.warehouse.sys.db.mysql;

public class OperationWasNotPerformedException extends DBException {
    public OperationWasNotPerformedException(String message) {
        super(message);
    }

    public OperationWasNotPerformedException() {
    }

    public OperationWasNotPerformedException(String message, Exception ex) {
        super(message, ex);
    }

    public OperationWasNotPerformedException(Exception ex) {
        super(ex);
    }
}
