package telran.drones;
import telran.drones.exceptions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.drones.*;
import telran.drones.dto.DroneDto;
import telran.drones.dto.DroneMedication;
import telran.drones.dto.ModelType;
import telran.drones.service.DronesService;
@WebMvcTest 
public class DroneControllerTest {
private static final String DRONE_ALREADY_EXISTS_MESSAGE = "Drone already exists";
private static final String DRONE_NOT_FOUND_MESSAGE = "Drone not found";
@MockBean
DronesService dronesService;
@Autowired
MockMvc mockMvc;
@Autowired
ObjectMapper mapper;
ModelType model1=ModelType.Cruiserweight;
DroneDto droneDto=new DroneDto("111111", model1);
DroneDto droneDto1=new DroneDto("22222", model1);
DroneDto droneDto2=new DroneDto("22222", model1);
DroneMedication droneMedication=new DroneMedication("11111", "11111");

@Test
void testRegisterDrone()throws Exception {
	
	when(dronesService.registerDrone(droneDto)).thenReturn(droneDto);
	String jsonDroneDto = mapper.writeValueAsString(droneDto); 
	String actualJSON = mockMvc.perform(post("http://localhost:8080/drones/register").contentType(MediaType.APPLICATION_JSON)
			.content(jsonDroneDto)).andExpect(status().isOk()).andReturn().getResponse()
	.getContentAsString();
	assertEquals(jsonDroneDto, actualJSON );
	
}
@Test
void testLoadDrone() throws Exception{
	
	when(dronesService.loadDrone(droneMedication)).thenReturn(droneMedication);
	String jsonDroneMedication=mapper.writeValueAsString(droneMedication);
	String actualJson=mockMvc.perform(post("http://localhost:8080/drones/load").contentType(MediaType.APPLICATION_JSON).content(jsonDroneMedication)).andExpect(status().isOk()).andReturn().getResponse()
	.getContentAsString();
	assertEquals(jsonDroneMedication,actualJson);
}
@Test
void testAddPersonAlreadyExists() throws Exception {
	when(dronesService.registerDrone(droneDto)).thenThrow(new IllegalStateException(DRONE_ALREADY_EXISTS_MESSAGE));
	String jsonPersonDto = mapper.writeValueAsString(droneDto); 
	String response = mockMvc.perform(post("http://localhost:8080/drones/register").contentType(MediaType.APPLICATION_JSON)
			.content(jsonPersonDto)).andExpect(status().isBadRequest()).andReturn().getResponse()
	.getContentAsString();
	assertEquals(DRONE_ALREADY_EXISTS_MESSAGE, response);

}
@Test
void testLoadDroneNotFound() throws Exception{
	testLoadDroneNotFound(DRONE_NOT_FOUND_MESSAGE);
}

private void testLoadDroneNotFound(String message)throws Exception {
	when(dronesService.loadDrone(droneMedication)).thenThrow(new NotFoundException(message));
	String jsonDroneMedication = mapper.writeValueAsString(droneMedication);
	String response = mockMvc.perform(post("http://localhost:8080/drones/load")
			.contentType(MediaType.APPLICATION_JSON).content(jsonDroneMedication))
			.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
	assertEquals(message, response);
}
}
