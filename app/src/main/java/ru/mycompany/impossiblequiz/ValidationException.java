package ru.mycompany.impossiblequiz;

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
