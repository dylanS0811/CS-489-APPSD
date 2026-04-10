package edu.miu.cs.cs489appsd.lab6.adsapp.repository;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPatientNumber(String patientNumber);

    List<Patient> findAllByOrderByLastNameAscFirstNameAsc();

    @Query("""
            select distinct p
            from Patient p
            left join p.mailingAddress a
            where lower(p.patientNumber) like lower(concat('%', :searchTerm, '%'))
               or lower(p.firstName) like lower(concat('%', :searchTerm, '%'))
               or lower(p.lastName) like lower(concat('%', :searchTerm, '%'))
               or lower(p.phoneNumber) like lower(concat('%', :searchTerm, '%'))
               or lower(p.email) like lower(concat('%', :searchTerm, '%'))
               or lower(a.street) like lower(concat('%', :searchTerm, '%'))
               or lower(a.city) like lower(concat('%', :searchTerm, '%'))
               or lower(a.state) like lower(concat('%', :searchTerm, '%'))
               or lower(a.zipCode) like lower(concat('%', :searchTerm, '%'))
            order by p.lastName asc, p.firstName asc
            """)
    List<Patient> searchPatients(@Param("searchTerm") String searchTerm);
}
