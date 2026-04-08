package edu.miu.cs.cs489appsd.lab6.adsapp.repository;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByOrderByAppointmentDateAscAppointmentTimeAsc();

    List<Appointment> findByPatient_PatientNumberOrderByAppointmentDateAscAppointmentTimeAsc(String patientNumber);

    List<Appointment> findBySurgery_SurgeryNumberOrderByAppointmentDateAscAppointmentTimeAsc(String surgeryNumber);
}
