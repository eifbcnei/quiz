package ru.mycompany.impossiblequiz.exceptions;

public class ValidationException extends Exception {
    private ExceptionCodes code;

    public ValidationException(ExceptionCodes code) {
        super();
        this.code = code;
    }

    public ExceptionCodes getCode() {
        return code;
    }
}
