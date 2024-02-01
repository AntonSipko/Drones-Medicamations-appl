package telran.drones.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.drones.api.PropertiesNames;
import telran.drones.dto.*;
import telran.drones.exceptions.*;

import telran.drones.model.*;
import telran.drones.repo.*;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class DronesServiceImpl implements DronesService {
	final DronesRepo droneRepo;
	final MedicationRepo medicationRepo;
	final EventLogRepo logRepo;
	final DronesModelRepo droneModelRepo;
	@Value("${" + PropertiesNames.CAPACITY_THRESHOLD + ":25}")
	int capacityThreshold;
	

	@Override
	@Transactional
	public DroneDto registerDrone(DroneDto droneDto) {
		log.debug("service got drone DTO: {}", droneDto);
		if (droneRepo.existsById(droneDto.number())) {
			throw new DroneAlreadyExistException();
		}
		Drone drone = Drone.of(droneDto);
		
		DroneModel droneModel = droneModelRepo.findById(droneDto.modelType())
				.orElseThrow(() -> new ModelNotFoundException());
		drone.setModel(droneModel);
		log.debug("drone object is {}", drone);
		droneRepo.save(drone);
		return droneDto;
	}

	@Override
	@Transactional(readOnly = false)
	public DroneMedication loadDrone(DroneMedication droneMedication) {
		String droneNumber = droneMedication.droneNumber();
		String medicationCode = droneMedication.medicationCode();
		log.debug("received: droneNumber={}, medicationCode={}",droneNumber ,
				medicationCode);
		log.debug("capacity threshold is {}", capacityThreshold);
		Drone drone = droneRepo.findById(droneNumber).orElseThrow(() -> new DroneNotFoundException());
		log.debug("found drone: {}", drone);
		Medication medication = medicationRepo.findById(medicationCode)
				.orElseThrow(() -> new MedicationNotFoundException());
		log.debug("found medication: {}", medication);
		if (drone.getState() != State.IDLE) {
			throw new IllegalDroneStateException();
		}

		if (drone.getBatteryCapacity() < capacityThreshold) {
			throw new LowBatteryCapacityException();
		}
		if (drone.getModel().getWeight() < medication.getWeight()) {
			throw new IllegalMedicationWeightException();
		}
		drone.setState(State.LOADING);
		//EventLog(LocalDateTime timestamp, String droneNumber, State state, int batteryCapacity) 
		EventLog eventLog = new EventLog(LocalDateTime.now(), drone.getNumber(), drone.getState(),
				drone.getBatteryCapacity(), medicationCode);
		logRepo.save(eventLog);
		
		log.debug("saved log: {}", eventLog);

		return droneMedication;
	}

	@Override
	public List<String> checkMedicationItems(String droneNumber) {
		Drone drone=droneRepo.findById(droneNumber).orElseThrow(()->new DroneNotFoundException());
		List<String>medicationList=new ArrayList<>();
		List<EventLog>eventsList=logRepo.findAll();
		for(EventLog log:eventsList) {
			if(log.getDroneNumber()==drone.getNumber()) {
				medicationList.add(droneNumber.toString());
			}
			
		}
		return medicationList;
	}

	@Override
	public List<String> checkAvailableDrones() {
		List<String>availableDronesNumbers=new ArrayList<>();
		List<Drone>allDrones=droneRepo.findAll();
		for(Drone drone:allDrones) {
			if(drone.getBatteryCapacity()>25&&drone.getState()==State.IDLE) {
				availableDronesNumbers.add(drone.getNumber());
			}
		}
		if(availableDronesNumbers.isEmpty()) {
			throw new DroneNotFoundException();
		}else {
			return availableDronesNumbers;
		}
	}

	@Override
	public int checkBatteryCapacity(String droneNumber) {
		Drone drone=droneRepo.findById(droneNumber).orElseThrow(()->new DroneNotFoundException());
		return drone.getBatteryCapacity();
	}

	@Override
	public List<DroneItemsAmount> checkDroneLoadedItemAmounts() {
	    HashMap<String, Long> dronesLoaded = new HashMap<>();
	    List<EventLog> events = logRepo.findAll();

	    for (EventLog event : events) {
	        String droneNumber = event.getDroneNumber();
	        dronesLoaded.putIfAbsent(droneNumber, 0L); 
	        dronesLoaded.computeIfPresent(droneNumber, (key, value) -> value + 1);
	    }

	    List<DroneItemsAmount> res = convertMapToList(dronesLoaded);
	    res.sort(Comparator.comparingLong(DroneItemsAmount::getAmount).reversed());

	    return res;
	}

	private List<DroneItemsAmount> convertMapToList(HashMap<String, Long> dronesLoaded) {
	    return dronesLoaded.entrySet()
	            .stream()
	            .map(entry -> new DroneItemsAmountImpl(entry.getKey(), entry.getValue()))
	            .collect(Collectors.toList());
	}
}
