package edu.miu.cs.cs489appsd.lab6.adsapp.repository;

import edu.miu.cs.cs489appsd.lab6.adsapp.model.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurgeryRepository extends JpaRepository<Surgery, Long> {

    Optional<Surgery> findBySurgeryNumber(String surgeryNumber);

    List<Surgery> findAllByOrderBySurgeryNumberAsc();
}
