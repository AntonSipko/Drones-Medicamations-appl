package telran.drones.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.drones.service.model.Drone;

public interface DroneRepo extends JpaRepository<Drone, String> {
	

}
