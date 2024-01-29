package telran.drones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.drones.*;
import telran.drones.dto.DroneDto;
import telran.drones.dto.DroneMedication;
import telran.drones.dto.ModelType;
import telran.drones.service.DronesService;
@WebMvcTest 
public class DroneControllerTest {
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

@Test
void testRegisterDrone(DroneDto droneDto)throws Exception {
	when(dronesService.registerDrone(droneDto)).thenReturn(droneDto);
	String jsonDroneDto = mapper.writeValueAsString(droneDto); //conversion from carDto object to string JSON
	String actualJSON = mockMvc.perform(post("http://localhost:8080/drones").contentType(MediaType.APPLICATION_JSON)
			.content(jsonDroneDto)).andExpect(status().isOk()).andReturn().getResponse()
	.getContentAsString();
	assertEquals(jsonDroneDto, actualJSON );
	
}
@Test
void testLoadDrone(DroneMedication droneMedication) throws Exception{
	when(dronesService.loadDrone(droneMedication)).thenReturn(droneMedication);
	String jsonDroneMedication=mapper.writeValueAsString(droneMedication);
	String actualJson=mockMvc.perform(put("http://localhost:8080/drones").contentType(MediaType.APPLICATION_JSON).content(jsonDroneMedication)).andExpect(status().isOk()).andReturn().getResponse()
	.getContentAsString();
	assertEquals(jsonDroneMedication,actualJson);
}
}
