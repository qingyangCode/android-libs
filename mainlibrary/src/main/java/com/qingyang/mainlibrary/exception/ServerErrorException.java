package com.qingyang.mainlibrary.exception;

/**
 * Created by Jack Yang (jackyang@zepplabs.com) on 1/19/15.
 */
public class ServerErrorException extends Exception {
    public ServerErrorException() {
        super();
    }
    public ServerErrorException(final Throwable cause) {
        super(cause);
    }
}
