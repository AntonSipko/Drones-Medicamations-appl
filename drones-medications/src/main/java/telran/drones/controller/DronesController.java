package telran.drones.controller;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.drones.controller.DronesController;
import telran.drones.dto.DroneDto;
import telran.drones.dto.DroneMedication;
import telran.drones.service.DronesService;

@RestController
@RequestMapping("drones")
@RequiredArgsConstructor
@Slf4j
public class DronesController {
	final DronesService dronesService;
	@PostMapping("/register")
	DroneDto registerDrone(@RequestBody @Valid DroneDto droneDto) {
		log.debug("registerDrone: received Drone data: {}", droneDto);
		return dronesService.registerDrone(droneDto);
	}
	@PostMapping("/load")
	DroneMedication loadDrone(@RequestBody @Valid DroneMedication droneMedication) {
		log.debug("loadDrone: received DroneMedication data: {}", droneMedication);
		return dronesService.loadDrone(droneMedication);
		
		
	}
	
}
