package ru.mycompany.impossiblequiz.exceptions;

public enum ExceptionCodes {

    INVALID_NAME(0),
    INVALID_QUESTION(1),
    INVALID_AVATAR(2);

    public final int code;

    ExceptionCodes(int code) {
        this.code = code;
    }

}
