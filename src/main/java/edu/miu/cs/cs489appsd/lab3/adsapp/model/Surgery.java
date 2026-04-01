package edu.miu.cs.cs489appsd.lab3.adsapp.model;

public class Surgery {
    private final long surgeryId;
    private final String name;
    private final String locationAddress;
    private final String telephoneNumber;

    public Surgery(long surgeryId, String name, String locationAddress, String telephoneNumber) {
        this.surgeryId = surgeryId;
        this.name = name;
        this.locationAddress = locationAddress;
        this.telephoneNumber = telephoneNumber;
    }

    public long getSurgeryId() {
        return surgeryId;
    }

    public String getName() {
        return name;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }
}
