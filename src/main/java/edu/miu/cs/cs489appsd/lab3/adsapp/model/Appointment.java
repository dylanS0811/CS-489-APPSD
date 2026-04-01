package edu.miu.cs.cs489appsd.lab3.adsapp.model;

import java.time.LocalDateTime;

public class Appointment {
    private final long appointmentId;
    private final LocalDateTime appointmentDateTime;
    private final AppointmentStatus status;
    private final boolean confirmationEmailSent;
    private final Patient patient;
    private final Dentist dentist;
    private final Surgery surgery;
    private final OfficeManager bookedBy;

    public Appointment(long appointmentId, LocalDateTime appointmentDateTime, AppointmentStatus status,
                       boolean confirmationEmailSent, Patient patient, Dentist dentist,
                       Surgery surgery, OfficeManager bookedBy) {
        this.appointmentId = appointmentId;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.confirmationEmailSent = confirmationEmailSent;
        this.patient = patient;
        this.dentist = dentist;
        this.surgery = surgery;
        this.bookedBy = bookedBy;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public boolean isConfirmationEmailSent() {
        return confirmationEmailSent;
    }

    public Patient getPatient() {
        return patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public Surgery getSurgery() {
        return surgery;
    }

    public OfficeManager getBookedBy() {
        return bookedBy;
    }
}
