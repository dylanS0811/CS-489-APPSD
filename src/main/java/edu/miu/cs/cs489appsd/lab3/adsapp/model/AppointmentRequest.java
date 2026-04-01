package edu.miu.cs.cs489appsd.lab3.adsapp.model;

import java.time.LocalDateTime;

public class AppointmentRequest {
    private final long requestId;
    private final RequestChannel requestChannel;
    private final RequestType requestType;
    private final LocalDateTime preferredDateTime;
    private final RequestStatus status;
    private final Patient patient;
    private final OfficeManager processedBy;
    private final Appointment relatedAppointment;

    public AppointmentRequest(long requestId, RequestChannel requestChannel, RequestType requestType,
                              LocalDateTime preferredDateTime, RequestStatus status, Patient patient,
                              OfficeManager processedBy, Appointment relatedAppointment) {
        this.requestId = requestId;
        this.requestChannel = requestChannel;
        this.requestType = requestType;
        this.preferredDateTime = preferredDateTime;
        this.status = status;
        this.patient = patient;
        this.processedBy = processedBy;
        this.relatedAppointment = relatedAppointment;
    }

    public long getRequestId() {
        return requestId;
    }

    public RequestChannel getRequestChannel() {
        return requestChannel;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public LocalDateTime getPreferredDateTime() {
        return preferredDateTime;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Patient getPatient() {
        return patient;
    }

    public OfficeManager getProcessedBy() {
        return processedBy;
    }

    public Appointment getRelatedAppointment() {
        return relatedAppointment;
    }
}
