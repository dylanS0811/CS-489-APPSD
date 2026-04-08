package edu.miu.cs.cs489appsd.lab6.adsapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private AppointmentStatus status;

    @Column(nullable = false)
    private boolean confirmationSent;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dentist_id", nullable = false)
    private Dentist dentist;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "surgery_id", nullable = false)
    private Surgery surgery;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "booked_by_user_id", nullable = false)
    private User bookedBy;

    protected Appointment() {
    }

    public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, AppointmentStatus status,
                       boolean confirmationSent, Patient patient, Dentist dentist, Surgery surgery, User bookedBy) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.confirmationSent = confirmationSent;
        this.patient = patient;
        this.dentist = dentist;
        this.surgery = surgery;
        this.bookedBy = bookedBy;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public boolean isConfirmationSent() {
        return confirmationSent;
    }

    public void setConfirmationSent(boolean confirmationSent) {
        this.confirmationSent = confirmationSent;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public Surgery getSurgery() {
        return surgery;
    }

    public void setSurgery(Surgery surgery) {
        this.surgery = surgery;
    }

    public User getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(User bookedBy) {
        this.bookedBy = bookedBy;
    }

    @Override
    public String toString() {
        return "Appointment{appointmentId=%d, date=%s, time=%s, patient='%s', dentist='%s', surgery='%s', bookedBy='%s'}"
                .formatted(appointmentId, appointmentDate, appointmentTime, patient.getFullName(),
                        dentist.getFullName(), surgery.getSurgeryNumber(), bookedBy.getUsername());
    }
}
