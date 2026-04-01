package edu.miu.cs.cs489appsd.lab3.adsapp.model;

import java.time.LocalDate;
import java.util.Collection;

public class Patient extends Person {
    private final long patientId;
    private final String mailingAddress;
    private final LocalDate dateOfBirth;

    public Patient(long patientId, String firstName, String lastName, String phoneNumber,
                   String email, String mailingAddress, LocalDate dateOfBirth) {
        super(firstName, lastName, phoneNumber, email);
        this.patientId = patientId;
        this.mailingAddress = mailingAddress;
        this.dateOfBirth = dateOfBirth;
    }

    public long getPatientId() {
        return patientId;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean hasOutstandingUnpaidBill(Collection<DentalServiceBill> bills) {
        return bills.stream()
                .filter(bill -> bill.getPatient().getPatientId() == patientId)
                .anyMatch(DentalServiceBill::isOutstanding);
    }

    public boolean canRequestNewAppointment(Collection<DentalServiceBill> bills) {
        return !hasOutstandingUnpaidBill(bills);
    }
}
