package edu.miu.cs.cs489appsd.lab6.adsapp.service;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Address;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Appointment;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.AppointmentStatus;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Dentist;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Patient;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Role;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.Surgery;
import edu.miu.cs.cs489appsd.lab6.adsapp.model.User;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.AppointmentRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.DentistRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.PatientRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.RoleRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.SurgeryRepository;
import edu.miu.cs.cs489appsd.lab6.adsapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClinicManagementService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH.mm");
    private static final String ROW_FORMAT = "%-18s %-6s %-15s %-11s %-7s %-7s";

    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final SurgeryRepository surgeryRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ClinicManagementService(PatientRepository patientRepository,
                                   DentistRepository dentistRepository,
                                   SurgeryRepository surgeryRepository,
                                   AppointmentRepository appointmentRepository,
                                   UserRepository userRepository,
                                   RoleRepository roleRepository) {
        this.patientRepository = patientRepository;
        this.dentistRepository = dentistRepository;
        this.surgeryRepository = surgeryRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void seedSampleData() {
        clearExistingData();

        Role officeManager = roleRepository.save(new Role("OFFICE_MANAGER", "Books and manages appointments"));
        Role administrator = roleRepository.save(new Role("ADMINISTRATOR", "Maintains master data"));

        User oliviaMorgan = userRepository.save(new User(
                "olivia.morgan",
                "welcome1",
                "Olivia",
                "Morgan",
                "olivia.morgan@adsclinic.com",
                officeManager
        ));
        userRepository.save(new User(
                "ethan.reed",
                "welcome1",
                "Ethan",
                "Reed",
                "ethan.reed@adsclinic.com",
                administrator
        ));

        Dentist tonySmith = dentistRepository.save(new Dentist(
                "DEN100",
                "Tony",
                "Smith",
                "641-451-1000",
                "tony.smith@adsclinic.com",
                "General Dentistry"
        ));
        Dentist helenPearson = dentistRepository.save(new Dentist(
                "DEN101",
                "Helen",
                "Pearson",
                "641-451-1001",
                "helen.pearson@adsclinic.com",
                "Orthodontics"
        ));
        Dentist robinPlevin = dentistRepository.save(new Dentist(
                "DEN102",
                "Robin",
                "Plevin",
                "641-451-1002",
                "robin.plevin@adsclinic.com",
                "Restorative Dentistry"
        ));

        Surgery surgery10 = surgeryRepository.save(new Surgery(
                "S10",
                "North Hill Surgery",
                "641-451-2010",
                new Address("10 North Hill Rd", "Fairfield", "IA", "52556")
        ));
        Surgery surgery13 = surgeryRepository.save(new Surgery(
                "S13",
                "Riverside Surgery",
                "641-451-2013",
                new Address("13 Riverside Ave", "Fairfield", "IA", "52556")
        ));
        Surgery surgery15 = surgeryRepository.save(new Surgery(
                "S15",
                "Downtown Surgery",
                "641-451-2015",
                new Address("15 Main St", "Fairfield", "IA", "52556")
        ));

        Patient gillianWhite = patientRepository.save(new Patient(
                "P100",
                "Gillian",
                "White",
                "641-451-3000",
                "gillian.white@example.com",
                LocalDate.of(1989, 4, 12),
                new Address("100 Maple St", "Fairfield", "IA", "52556")
        ));
        Patient jillBell = patientRepository.save(new Patient(
                "P105",
                "Jill",
                "Bell",
                "641-451-3005",
                "jill.bell@example.com",
                LocalDate.of(1991, 7, 21),
                new Address("105 Cedar Ave", "Fairfield", "IA", "52556")
        ));
        Patient ianMacKay = patientRepository.save(new Patient(
                "P108",
                "Ian",
                "MacKay",
                "641-451-3008",
                "ian.mackay@example.com",
                LocalDate.of(1987, 11, 3),
                new Address("108 Birch Ln", "Fairfield", "IA", "52556")
        ));
        Patient johnWalker = patientRepository.save(new Patient(
                "P110",
                "John",
                "Walker",
                "641-451-3010",
                "john.walker@example.com",
                LocalDate.of(1994, 2, 9),
                new Address("110 Walnut Dr", "Fairfield", "IA", "52556")
        ));

        appointmentRepository.saveAll(List.of(
                new Appointment(LocalDate.of(2013, 9, 12), LocalTime.of(10, 0), AppointmentStatus.COMPLETED, true, gillianWhite, tonySmith, surgery15, oliviaMorgan),
                new Appointment(LocalDate.of(2013, 9, 12), LocalTime.of(12, 0), AppointmentStatus.COMPLETED, true, jillBell, tonySmith, surgery15, oliviaMorgan),
                new Appointment(LocalDate.of(2013, 9, 12), LocalTime.of(10, 0), AppointmentStatus.COMPLETED, true, ianMacKay, helenPearson, surgery10, oliviaMorgan),
                new Appointment(LocalDate.of(2013, 9, 14), LocalTime.of(14, 0), AppointmentStatus.COMPLETED, true, ianMacKay, helenPearson, surgery10, oliviaMorgan),
                new Appointment(LocalDate.of(2013, 9, 14), LocalTime.of(16, 30), AppointmentStatus.COMPLETED, true, jillBell, robinPlevin, surgery15, oliviaMorgan),
                new Appointment(LocalDate.of(2013, 9, 15), LocalTime.of(18, 0), AppointmentStatus.COMPLETED, true, johnWalker, robinPlevin, surgery13, oliviaMorgan)
        ));
    }

    private void clearExistingData() {
        appointmentRepository.deleteAll();
        appointmentRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();

        patientRepository.deleteAll();
        patientRepository.flush();

        surgeryRepository.deleteAll();
        surgeryRepository.flush();

        dentistRepository.deleteAll();
        dentistRepository.flush();

        roleRepository.deleteAll();
        roleRepository.flush();
    }

    public List<String> runCrudShowcase() {
        List<String> lines = new ArrayList<>();

        lines.add("Lab 6 - ADS Dental Surgeries Data Persistence");
        lines.add("=============================================");
        lines.add("Seeded reference data: %d users, %d roles, %d patients, %d dentists, %d surgeries, %d appointments"
                .formatted(
                        userRepository.count(),
                        roleRepository.count(),
                        patientRepository.count(),
                        dentistRepository.count(),
                        surgeryRepository.count(),
                        appointmentRepository.count()
                ));
        lines.add("");
        lines.add("Seeded appointment schedule");
        lines.addAll(formatAppointments(appointmentRepository.findAllByOrderByAppointmentDateAscAppointmentTimeAsc()));

        Patient newPatient = patientRepository.save(new Patient(
                "P120",
                "Sarah",
                "Connor",
                "641-451-3120",
                "sarah.connor@example.com",
                LocalDate.of(1990, 5, 13),
                new Address("120 Aspen Ct", "Fairfield", "IA", "52556")
        ));
        Appointment newAppointment = appointmentRepository.save(new Appointment(
                LocalDate.of(2013, 9, 16),
                LocalTime.of(9, 30),
                AppointmentStatus.SCHEDULED,
                true,
                newPatient,
                dentistRepository.findByFirstNameAndLastName("Tony", "Smith").orElseThrow(),
                surgeryRepository.findBySurgeryNumber("S15").orElseThrow(),
                userRepository.findByUsername("olivia.morgan").orElseThrow()
        ));

        lines.add("");
        lines.add("CREATE");
        lines.add("Added patient -> " + newPatient);
        lines.add("Added appointment -> " + formatAppointmentSummary(newAppointment));

        Patient patientToRead = patientRepository.findByPatientNumber("P105").orElseThrow();
        lines.add("");
        lines.add("READ");
        lines.add("Fetched patient P105 -> " + patientToRead);
        lines.add("Appointments booked in surgery S15");
        lines.addAll(formatAppointments(
                appointmentRepository.findBySurgery_SurgeryNumberOrderByAppointmentDateAscAppointmentTimeAsc("S15")
        ));

        patientToRead.setEmail("jill.bell@adsclinic.com");
        patientToRead.getMailingAddress().setStreet("105 Updated Cedar Ave");
        patientToRead.getMailingAddress().setZipCode("52556-0105");
        Patient updatedPatient = patientRepository.save(patientToRead);

        lines.add("");
        lines.add("UPDATE");
        lines.add("Updated patient P105 -> " + updatedPatient);

        appointmentRepository.delete(newAppointment);
        patientRepository.delete(newPatient);

        lines.add("");
        lines.add("DELETE");
        lines.add("Deleted temporary appointment id %d and temporary patient P120.".formatted(newAppointment.getAppointmentId()));
        lines.add("Final appointment count -> " + appointmentRepository.count());

        return lines;
    }

    private List<String> formatAppointments(List<Appointment> appointments) {
        List<String> lines = new ArrayList<>();
        lines.add(String.format(ROW_FORMAT, "dentistName", "patNo", "patName", "date", "time", "surgery"));
        appointments.forEach(appointment -> lines.add(formatAppointmentSummary(appointment)));
        return lines;
    }

    private String formatAppointmentSummary(Appointment appointment) {
        return String.format(
                ROW_FORMAT,
                appointment.getDentist().getFullName(),
                appointment.getPatient().getPatientNumber(),
                appointment.getPatient().getFullName(),
                appointment.getAppointmentDate().format(DATE_FORMATTER),
                appointment.getAppointmentTime().format(TIME_FORMATTER),
                appointment.getSurgery().getSurgeryNumber()
        );
    }
}
