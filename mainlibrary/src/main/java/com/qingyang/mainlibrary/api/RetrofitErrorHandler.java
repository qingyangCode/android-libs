package com.qingyang.mainlibrary.api;

import com.qingyang.mainlibrary.MainApplication;
import com.qingyang.mainlibrary.exception.InvalidCredentialException;
import com.qingyang.mainlibrary.exception.NetworkConnectionException;
import com.qingyang.mainlibrary.exception.ServerErrorException;
import org.apache.http.HttpStatus;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by QingYang on 15/9/8.
 */
public class RetrofitErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        switch (cause.getKind()){
            case NETWORK:
                return new NetworkConnectionException(cause);
            case HTTP:
            {
                Response r = cause.getResponse();
                if(r != null){
                    switch (r.getStatus()) {
                        case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                            return new ServerErrorException(cause);
                        case HttpStatus.SC_UNAUTHORIZED:
                            MainApplication.getMainApplication().onUnAuthorized();
                            return new InvalidCredentialException();
                    }

                }
            }
            case CONVERSION:
            default:
                break;
        }
        return cause;
    }
}
