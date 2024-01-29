package telran.drones.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.drones.dto.ModelType;
import telran.drones.service.model.DroneModel;
import telran.drones.service.model.ModelWeight;

public interface DroneModelRepo extends JpaRepository<DroneModel,ModelWeight > {

}
