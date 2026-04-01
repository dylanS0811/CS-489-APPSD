package edu.miu.cs.cs489appsd.lab3.adsapp.model;

public class OfficeManager extends Person {
    private final long officeManagerId;

    public OfficeManager(long officeManagerId, String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
        this.officeManagerId = officeManagerId;
    }

    public long getOfficeManagerId() {
        return officeManagerId;
    }
}
