package edu.miu.cs.cs489appsd.lab7.adswebapi.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String message) {
        super(message);
    }
}
