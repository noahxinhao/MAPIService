package com.noah.mapi.exception;

import org.apache.log4j.Logger;

/**
 * Created by jacobdong on 15/9/1.
 */
public class ServiceException extends Exception {
    private final Logger LOG = Logger.getLogger(ServiceException.class);

    private ServiceError error;

    public ServiceException(ServiceError error) {
        this.error = error;
        LOG.error("[" + error.getDesc() + " # " + error.getCode() + "]");
    }


    public ServiceError getError() {
        return error;
    }

    public void setError(ServiceError error) {
        this.error = error;
    }
}
