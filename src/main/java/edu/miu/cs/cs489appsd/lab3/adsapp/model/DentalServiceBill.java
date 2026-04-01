package edu.miu.cs.cs489appsd.lab3.adsapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DentalServiceBill {
    private final long billId;
    private final LocalDate issueDate;
    private final BigDecimal amount;
    private final BillStatus status;
    private final String description;
    private final Patient patient;
    private final Appointment appointment;

    public DentalServiceBill(long billId, LocalDate issueDate, BigDecimal amount, BillStatus status,
                             String description, Patient patient, Appointment appointment) {
        this.billId = billId;
        this.issueDate = issueDate;
        this.amount = amount;
        this.status = status;
        this.description = description;
        this.patient = patient;
        this.appointment = appointment;
    }

    public long getBillId() {
        return billId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BillStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Patient getPatient() {
        return patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public boolean isOutstanding() {
        return status != BillStatus.PAID;
    }
}
