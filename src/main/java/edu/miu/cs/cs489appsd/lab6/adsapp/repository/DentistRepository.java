package edu.miu.cs.cs489appsd.lab6.adsapp.repository;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DentistRepository extends JpaRepository<Dentist, Long> {

    Optional<Dentist> findByDentistCode(String dentistCode);

    Optional<Dentist> findByFirstNameAndLastName(String firstName, String lastName);

    List<Dentist> findAllByOrderByLastNameAscFirstNameAsc();
}
