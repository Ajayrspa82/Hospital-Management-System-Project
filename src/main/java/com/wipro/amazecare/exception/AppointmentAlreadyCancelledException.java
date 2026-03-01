package com.wipro.amazecare.exception;



public class AppointmentAlreadyCancelledException extends RuntimeException {

    public AppointmentAlreadyCancelledException(String message) {
        super(message);
    }
}