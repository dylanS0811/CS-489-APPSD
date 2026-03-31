package edu.miu.cs.cs489appsd.lab2b.pamsapp.model;

import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private final long patientId;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final String mailingAddress;
    private final LocalDate dateOfBirth;

    public Patient(long patientId, String firstName, String lastName, String phoneNumber,
                   String email, String mailingAddress, LocalDate dateOfBirth) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.mailingAddress = mailingAddress;
        this.dateOfBirth = dateOfBirth;
    }

    public long getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge(LocalDate referenceDate) {
        return Period.between(dateOfBirth, referenceDate).getYears();
    }
}
