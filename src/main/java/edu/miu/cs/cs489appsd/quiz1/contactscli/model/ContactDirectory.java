package edu.miu.cs.cs489appsd.quiz1.contactscli.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ContactDirectory {
    private final List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        if (contact == null) {
            return;
        }
        contacts.add(contact);
    }

    public boolean updateContact(Contact updatedContact) {
        if (updatedContact == null) {
            return false;
        }

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getContactId() == updatedContact.getContactId()) {
                contacts.set(i, updatedContact);
                return true;
            }
        }
        return false;
    }

    public boolean deleteContact(long contactId) {
        return contacts.removeIf(contact -> contact.getContactId() == contactId);
    }

    public List<Contact> searchContacts(String keyword) {
        String normalizedKeyword = normalize(keyword);
        if (normalizedKeyword.isBlank()) {
            return List.copyOf(contacts);
        }

        return contacts.stream()
                .filter(contact -> matches(contact, normalizedKeyword))
                .toList();
    }

    public Contact mergeContacts(long primaryId, long duplicateId) {
        if (primaryId == duplicateId) {
            throw new IllegalArgumentException("Primary and duplicate contact IDs must be different.");
        }

        Contact primaryContact = findById(primaryId);
        Contact duplicateContact = findById(duplicateId);

        if (primaryContact == null || duplicateContact == null) {
            throw new IllegalArgumentException("Both contacts must exist before they can be merged.");
        }

        primaryContact.setFirstName(prefer(primaryContact.getFirstName(), duplicateContact.getFirstName()));
        primaryContact.setLastName(prefer(primaryContact.getLastName(), duplicateContact.getLastName()));
        primaryContact.setCompany(prefer(primaryContact.getCompany(), duplicateContact.getCompany()));
        primaryContact.setJobTitle(prefer(primaryContact.getJobTitle(), duplicateContact.getJobTitle()));

        duplicateContact.getPhoneNumbers().forEach(primaryContact::addPhoneNumber);
        duplicateContact.getEmailAddresses().forEach(primaryContact::addEmailAddress);

        deleteContact(duplicateId);
        return primaryContact;
    }

    public List<Contact> getContactsSortedByLastName() {
        return contacts.stream()
                .sorted(Comparator.comparing((Contact contact) -> normalize(contact.getLastName()))
                        .thenComparing(contact -> normalize(contact.getFirstName()))
                        .thenComparingLong(Contact::getContactId))
                .toList();
    }

    private Contact findById(long contactId) {
        return contacts.stream()
                .filter(contact -> contact.getContactId() == contactId)
                .findFirst()
                .orElse(null);
    }

    private boolean matches(Contact contact, String keyword) {
        return contains(contact.getFirstName(), keyword)
                || contains(contact.getLastName(), keyword)
                || contains(contact.getCompany(), keyword)
                || contains(contact.getJobTitle(), keyword)
                || contact.getPhoneNumbers().stream().anyMatch(phone -> contains(phone.getNumber(), keyword) || contains(phone.getLabel(), keyword))
                || contact.getEmailAddresses().stream().anyMatch(email -> contains(email.getAddress(), keyword) || contains(email.getLabel(), keyword));
    }

    private boolean contains(String value, String keyword) {
        return normalize(value).contains(keyword);
    }

    private String prefer(String primaryValue, String duplicateValue) {
        return normalize(primaryValue).isBlank() ? duplicateValue : primaryValue;
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.US);
    }
}
