package edu.miu.cs.cs489appsd.lab2b.pamsapp;

import edu.miu.cs.cs489appsd.lab2b.pamsapp.model.Patient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PAMSApp {

    public static void main(String[] args) throws IOException {
        LocalDate referenceDate = args.length > 0 ? LocalDate.parse(args[0]) : LocalDate.now();
        List<Patient> patients = loadPatients();
        List<Patient> sortedPatients = patients.stream()
                .sorted(Comparator.comparing((Patient patient) -> patient.getAge(referenceDate)).reversed()
                        .thenComparing(Patient::getLastName)
                        .thenComparing(Patient::getFirstName))
                .collect(Collectors.toList());

        String jsonOutput = buildPatientsJson(sortedPatients, referenceDate);
        Path outputPath = Path.of("outputs", "lab2b", "patients-by-age.json");
        Files.createDirectories(outputPath.getParent());
        Files.writeString(
                outputPath,
                jsonOutput + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        System.out.println("Patients JSON written to: " + outputPath.toAbsolutePath());
        System.out.println("Reference Date: " + referenceDate);
        System.out.println(jsonOutput);
    }

    private static List<Patient> loadPatients() {
        return List.of(
                new Patient(1L, "Daniel", "Agar", "(641) 123-0009", "dagar@m.as", "1 N Street", LocalDate.parse("1987-01-19")),
                new Patient(2L, "Ana", "Smith", null, "amsith@te.edu", null, LocalDate.parse("1948-12-05")),
                new Patient(3L, "Marcus", "Garvey", "(123) 292-0018", null, "4 East Ave", LocalDate.parse("2001-09-18")),
                new Patient(4L, "Jeff", "Goldbloom", "(999) 165-1192", "jgold@es.co.za", null, LocalDate.parse("1995-02-28")),
                new Patient(5L, "Mary", "Washington", null, null, "30 W Burlington", LocalDate.parse("1932-05-31"))
        );
    }

    private static String buildPatientsJson(List<Patient> patients, LocalDate referenceDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            sb.append("  {\n");
            sb.append("    \"patientId\": ").append(patient.getPatientId()).append(",\n");
            sb.append("    \"firstName\": ").append(toJsonString(patient.getFirstName())).append(",\n");
            sb.append("    \"lastName\": ").append(toJsonString(patient.getLastName())).append(",\n");
            sb.append("    \"phoneNumber\": ").append(toJsonString(patient.getPhoneNumber())).append(",\n");
            sb.append("    \"email\": ").append(toJsonString(patient.getEmail())).append(",\n");
            sb.append("    \"mailingAddress\": ").append(toJsonString(patient.getMailingAddress())).append(",\n");
            sb.append("    \"dateOfBirth\": ").append(toJsonString(patient.getDateOfBirth().toString())).append(",\n");
            sb.append("    \"age\": ").append(patient.getAge(referenceDate)).append("\n");
            sb.append("  }");

            if (i < patients.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
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
