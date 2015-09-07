package com.qingyang.mainlibrary.exception;

/**
 * Created by Jack Yang (jackyang@zepplabs.com) on 1/19/15.
 */
public class InvalidCredentialException extends Exception {
    public InvalidCredentialException() {
        super();
    }
    public InvalidCredentialException(String message){
        super(message);
    }
}
