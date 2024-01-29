package telran.drones.dto;
import jakarta.validation.constraints.*;
import static telran.drones.api.ValidationCostants.*;

import java.util.Objects;

import jakarta.validation.constraints.NotEmpty;


//TODO add validation constraints
public record DroneDto(@NotEmpty(message=MISSING_DRONE_NUMBER)@Pattern(regexp=DRONE_NUMBER_REGEXP,message= WRONG_DRONE_NUMBER)String number,@NotEmpty(message=MISSING_DRONE_MODEL)ModelType modelType) {
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DroneDto other = (DroneDto) obj;
		return Objects.equals(number, other.number);
	}
	

}
