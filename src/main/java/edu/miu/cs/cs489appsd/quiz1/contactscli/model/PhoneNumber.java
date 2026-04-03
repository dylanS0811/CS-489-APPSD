package edu.miu.cs.cs489appsd.quiz1.contactscli.model;

import java.util.Objects;

public class PhoneNumber {
    private final String number;
    private final String label;

    public PhoneNumber(String number, String label) {
        this.number = number;
        this.label = label;
    }

    public String getNumber() {
        return number;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PhoneNumber phoneNumber)) {
            return false;
        }
        return normalize(number).equals(normalize(phoneNumber.number))
                && normalize(label).equals(normalize(phoneNumber.label));
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalize(number), normalize(label));
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
