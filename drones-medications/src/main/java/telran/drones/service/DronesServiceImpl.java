package telran.drones.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.cars.exceptions.IllegalCarsStateException;
import telran.cars.exceptions.ModelNotFoundException;
import telran.cars.service.model.Car;
import telran.cars.service.model.Model;
import telran.cars.service.model.ModelYear;
import telran.drones.dto.DroneDto;
import telran.drones.dto.DroneMedication;
import telran.drones.dto.ModelType;
import telran.drones.exceptions.IllegalDronesStateException;
import telran.drones.repo.DroneModelRepo;
import telran.drones.repo.DroneRepo;
import telran.drones.repo.MedicationRepo;
import telran.drones.service.model.DroneModel;


@Service
@RequiredArgsConstructor
@Slf4j
public class DronesServiceImpl implements DronesService {
	final DroneRepo droneRepo;
	final DroneModelRepo droneModelRepo;
	final MedicationRepo medicationRepo;
	@Override
	@Transactional
	public DroneDto registerDrone(DroneDto droneDto) {
		if(droneRepo.existsById(droneDto.number())) {
			throw new IllegalDronesStateException();
		}
		DroneModel model = DroneModelRepo.findById(new ModelWeight(droneDto.number(), droneDto.modelType()))
				.orElseThrow(() -> new ModelNotFoundException());
		Car car = Car.of(carDto);
		car.setModel(model);
		carRepo.save(car);
		log.debug("car {} has been saved", carDto);
		return carDto;
	}

	@Override
	public DroneMedication loadDrone(DroneMedication droneMedication) {
		// TODO Auto-generated method stub
		return null;
	}

}
