package edu.miu.cs.cs489appsd.lab3.adsapp;

import edu.miu.cs.cs489appsd.lab3.adsapp.model.Appointment;
import edu.miu.cs.cs489appsd.lab3.adsapp.model.AppointmentStatus;
import edu.miu.cs.cs489appsd.lab3.adsapp.model.BillStatus;
import edu.miu.cs.cs489appsd.lab3.adsapp.model.DentalServiceBill;
import edu.miu.cs.cs489appsd.lab3.adsapp.model.Dentist;
import edu.miu.cs.cs489appsd.lab3.adsapp.model.OfficeManager;
import edu.miu.cs.cs489appsd.lab3.adsapp.model.Patient;
import edu.miu.cs.cs489appsd.lab3.adsapp.model.Surgery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ADSApp {

    public static void main(String[] args) {
        printFunctionalRequirements();
        System.out.println();
        printBusinessRuleChecks();
    }

    private static void printFunctionalRequirements() {
        System.out.println("ADS Functional Requirements");

        List<String> functionalRequirements = FunctionalRequirementCatalog.getFunctionalRequirements();
        for (int i = 0; i < functionalRequirements.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, functionalRequirements.get(i));
        }
    }

    private static void printBusinessRuleChecks() {
        OfficeManager officeManager = new OfficeManager(
                9001L,
                "Martha",
                "Cole",
                "(515) 555-0110",
                "martha.cole@ads.com"
        );

        Dentist dentist = new Dentist(
                3001L,
                "Amelia",
                "Stone",
                "(515) 555-0144",
                "amelia.stone@ads.com",
                "Orthodontics"
        );

        Patient blockedPatient = new Patient(
                5001L,
                "James",
                "Foster",
                "(515) 555-0198",
                "james.foster@email.com",
                "14 Cedar Lane, Des Moines, IA",
                LocalDate.parse("1992-08-18")
        );

        Patient eligiblePatient = new Patient(
                5002L,
                "Olivia",
                "Reed",
                "(515) 555-0107",
                "olivia.reed@email.com",
                "87 Walnut Avenue, Des Moines, IA",
                LocalDate.parse("1988-02-06")
        );

        Surgery surgery = new Surgery(
                7001L,
                "ADS West Des Moines Surgery",
                "1250 Grand Avenue, West Des Moines, IA",
                "(515) 555-0135"
        );

        List<Appointment> weeklyAppointments = List.of(
                new Appointment(8001L, LocalDateTime.parse("2026-04-06T09:00"), AppointmentStatus.CONFIRMED, true, blockedPatient, dentist, surgery, officeManager),
                new Appointment(8002L, LocalDateTime.parse("2026-04-06T13:30"), AppointmentStatus.CONFIRMED, true, eligiblePatient, dentist, surgery, officeManager),
                new Appointment(8003L, LocalDateTime.parse("2026-04-07T10:15"), AppointmentStatus.CONFIRMED, true, blockedPatient, dentist, surgery, officeManager),
                new Appointment(8004L, LocalDateTime.parse("2026-04-08T11:00"), AppointmentStatus.CONFIRMED, true, eligiblePatient, dentist, surgery, officeManager),
                new Appointment(8005L, LocalDateTime.parse("2026-04-09T15:00"), AppointmentStatus.CONFIRMED, true, blockedPatient, dentist, surgery, officeManager)
        );

        List<DentalServiceBill> bills = List.of(
                new DentalServiceBill(
                        9501L,
                        LocalDate.parse("2026-03-20"),
                        new BigDecimal("245.00"),
                        BillStatus.UNPAID,
                        "Root canal balance",
                        blockedPatient,
                        weeklyAppointments.get(0)
                ),
                new DentalServiceBill(
                        9502L,
                        LocalDate.parse("2026-03-15"),
                        new BigDecimal("120.00"),
                        BillStatus.PAID,
                        "Routine cleaning",
                        eligiblePatient,
                        weeklyAppointments.get(1)
                )
        );

        LocalDateTime requestedDateTime = LocalDateTime.parse("2026-04-10T14:30");

        System.out.println("Key Business Rule Checks");
        System.out.printf(
                "Dr. %s can accept another appointment on %s: %s%n",
                dentist.getLastName(),
                requestedDateTime,
                dentist.canAcceptAppointmentInWeek(weeklyAppointments, requestedDateTime)
        );
        System.out.printf(
                "Patient %s can request a new appointment: %s%n",
                blockedPatient.getFullName(),
                blockedPatient.canRequestNewAppointment(bills)
        );
        System.out.printf(
                "Patient %s can request a new appointment: %s%n",
                eligiblePatient.getFullName(),
                eligiblePatient.canRequestNewAppointment(bills)
        );
    }
}
