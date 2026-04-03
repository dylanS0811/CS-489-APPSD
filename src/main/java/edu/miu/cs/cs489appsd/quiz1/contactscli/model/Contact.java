package edu.miu.cs.cs489appsd.quiz1.contactscli.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contact {
    private final long contactId;
    private String firstName;
    private String lastName;
    private String company;
    private String jobTitle;
    private final List<PhoneNumber> phoneNumbers;
    private final List<EmailAddress> emailAddresses;

    public Contact(long contactId, String firstName, String lastName, String company, String jobTitle) {
        this(contactId, firstName, lastName, company, jobTitle, List.of(), List.of());
    }

    public Contact(long contactId, String firstName, String lastName, String company, String jobTitle,
                   List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.jobTitle = jobTitle;
        this.phoneNumbers = new ArrayList<>(phoneNumbers);
        this.emailAddresses = new ArrayList<>(emailAddresses);
    }

    public long getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return Collections.unmodifiableList(phoneNumbers);
    }

    public List<EmailAddress> getEmailAddresses() {
        return Collections.unmodifiableList(emailAddresses);
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber != null && !phoneNumbers.contains(phoneNumber)) {
            phoneNumbers.add(phoneNumber);
        }
    }

    public void addEmailAddress(EmailAddress emailAddress) {
        if (emailAddress != null && !emailAddresses.contains(emailAddress)) {
            emailAddresses.add(emailAddress);
        }
    }
}
