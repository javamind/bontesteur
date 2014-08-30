package com.ninjamind.conference.exception;

/**
 * Created by ehret_g on 30/08/14.
 */
public class ConferenceNotFoundException extends Exception {
    /**
     *
     */
    public ConferenceNotFoundException() {
    }

    /**
     *
     * @param message
     */
    public ConferenceNotFoundException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public ConferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public ConferenceNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ConferenceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
