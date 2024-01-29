package telran.drones.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.drones.service.model.Medication;

public interface MedicationRepo extends JpaRepository<Medication, Integer>{

}
