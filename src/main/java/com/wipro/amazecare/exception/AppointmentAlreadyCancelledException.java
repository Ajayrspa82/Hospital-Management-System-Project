package com.wipro.amazecare.exception;



@SuppressWarnings("serial")
public class AppointmentAlreadyCancelledException extends RuntimeException {

    public AppointmentAlreadyCancelledException(String message) {
        super(message);
    }
}