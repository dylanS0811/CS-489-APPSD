package edu.miu.cs.cs489appsd.lab3.adsapp.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;

public class Dentist extends Person {
    private final long dentistId;
    private final String specialization;

    public Dentist(long dentistId, String firstName, String lastName, String phoneNumber, String email, String specialization) {
        super(firstName, lastName, phoneNumber, email);
        this.dentistId = dentistId;
        this.specialization = specialization;
    }

    public long getDentistId() {
        return dentistId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public long countAppointmentsInWeek(Collection<Appointment> appointments, LocalDateTime candidateDateTime) {
        LocalDate weekStart = candidateDateTime.toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate weekEnd = weekStart.plusDays(6);

        return appointments.stream()
                .filter(appointment -> appointment.getDentist().getDentistId() == dentistId)
                .map(Appointment::getAppointmentDateTime)
                .map(LocalDateTime::toLocalDate)
                .filter(date -> !date.isBefore(weekStart) && !date.isAfter(weekEnd))
                .count();
    }

    public boolean canAcceptAppointmentInWeek(Collection<Appointment> appointments, LocalDateTime candidateDateTime) {
        return countAppointmentsInWeek(appointments, candidateDateTime) < 5;
    }
}
