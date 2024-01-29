package telran.drones.dto;

import static telran.drones.api.ValidationCostants.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

//TODO add validation constraints
public record DroneMedication(@NotEmpty(message=MISSING_DRONE_NUMBER)@Pattern(regexp=DRONE_NUMBER_REGEXP,message= WRONG_DRONE_NUMBER)String droneNumber,@NotEmpty(message=MISSING_MED_CODE)@Pattern(regexp=MED_CODE_REGEXP,message=WRONG_MED_CODE) String medicationCode) {
	

}
