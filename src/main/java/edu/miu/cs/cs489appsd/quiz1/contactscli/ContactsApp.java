package edu.miu.cs.cs489appsd.quiz1.contactscli;

import edu.miu.cs.cs489appsd.quiz1.contactscli.model.Contact;
import edu.miu.cs.cs489appsd.quiz1.contactscli.model.ContactDirectory;
import edu.miu.cs.cs489appsd.quiz1.contactscli.model.EmailAddress;
import edu.miu.cs.cs489appsd.quiz1.contactscli.model.PhoneNumber;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ContactsApp {

    public static void main(String[] args) throws IOException {
        ContactDirectory contactDirectory = loadSampleContacts();
        String jsonOutput = buildContactsJson(contactDirectory.getContactsSortedByLastName());

        Path outputPath = Path.of("quiz1", "contacts-sorted.json");
        Files.createDirectories(outputPath.getParent());
        Files.writeString(
                outputPath,
                jsonOutput + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        System.out.println(jsonOutput);
    }

    private static ContactDirectory loadSampleContacts() {
        ContactDirectory directory = new ContactDirectory();

        Contact davidSanger = new Contact(1L, "David", "Sanger", "Argos LLC", "Sales Manager");
        davidSanger.addPhoneNumber(new PhoneNumber("240-133-0011", "Home"));
        davidSanger.addPhoneNumber(new PhoneNumber("240-112-0123", "Mobile"));
        davidSanger.addEmailAddress(new EmailAddress("dave.sang@gmail.com", "Home"));
        davidSanger.addEmailAddress(new EmailAddress("dsanger@argos.com", "Work"));

        Contact carlosJimenez = new Contact(2L, "Carlos", "Jimenez", "Zappos", "Director");

        Contact aliGafar = new Contact(3L, "Ali", "Gafar", "BMI Services", "HR Manager");
        aliGafar.addPhoneNumber(new PhoneNumber("412-116-9988", "Work"));
        aliGafar.addEmailAddress(new EmailAddress("ali@bmi.com", "Work"));

        directory.addContact(davidSanger);
        directory.addContact(carlosJimenez);
        directory.addContact(aliGafar);
        return directory;
    }

    private static String buildContactsJson(List<Contact> contacts) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            sb.append("  {\n");
            sb.append("    \"contactId\": ").append(contact.getContactId()).append(",\n");
            sb.append("    \"firstName\": ").append(toJsonString(contact.getFirstName())).append(",\n");
            sb.append("    \"lastName\": ").append(toJsonString(contact.getLastName())).append(",\n");
            sb.append("    \"company\": ").append(toJsonString(contact.getCompany())).append(",\n");
            sb.append("    \"jobTitle\": ").append(toJsonString(contact.getJobTitle())).append(",\n");
            sb.append("    \"phoneNumbers\": ").append(buildPhoneNumbersJson(contact.getPhoneNumbers())).append(",\n");
            sb.append("    \"emailAddresses\": ").append(buildEmailAddressesJson(contact.getEmailAddresses())).append("\n");
            sb.append("  }");

            if (i < contacts.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]");
        return sb.toString();
    }

    private static String buildPhoneNumbersJson(List<PhoneNumber> phoneNumbers) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < phoneNumbers.size(); i++) {
            PhoneNumber phoneNumber = phoneNumbers.get(i);
            sb.append("{");
            sb.append("\"number\": ").append(toJsonString(phoneNumber.getNumber())).append(", ");
            sb.append("\"label\": ").append(toJsonString(phoneNumber.getLabel()));
            sb.append("}");
            if (i < phoneNumbers.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static String buildEmailAddressesJson(List<EmailAddress> emailAddresses) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < emailAddresses.size(); i++) {
            EmailAddress emailAddress = emailAddresses.get(i);
            sb.append("{");
            sb.append("\"address\": ").append(toJsonString(emailAddress.getAddress())).append(", ");
            sb.append("\"label\": ").append(toJsonString(emailAddress.getLabel()));
            sb.append("}");
            if (i < emailAddresses.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private static String toJsonString(String value) {
        if (value == null) {
            return "null";
        }
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}
