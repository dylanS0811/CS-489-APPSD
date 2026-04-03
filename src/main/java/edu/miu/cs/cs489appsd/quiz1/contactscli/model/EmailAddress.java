package edu.miu.cs.cs489appsd.quiz1.contactscli.model;

import java.util.Objects;

public class EmailAddress {
    private final String address;
    private final String label;

    public EmailAddress(String address, String label) {
        this.address = address;
        this.label = label;
    }

    public String getAddress() {
        return address;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EmailAddress emailAddress)) {
            return false;
        }
        return normalize(address).equals(normalize(emailAddress.address))
                && normalize(label).equals(normalize(emailAddress.label));
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalize(address), normalize(label));
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
