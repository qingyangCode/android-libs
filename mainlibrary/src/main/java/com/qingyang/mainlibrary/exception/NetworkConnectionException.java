package com.qingyang.mainlibrary.exception;

public class NetworkConnectionException extends Exception {

  public NetworkConnectionException() {
    super();
  }
  public NetworkConnectionException(final Throwable cause) {
        super(cause);
    }

  public NetworkConnectionException(String message){
    super(message);
  }

}
