package telran.drones.model;
import jakarta.persistence.*;
import telran.drones.dto.DroneDto;
import telran.drones.dto.ModelType;
import telran.drones.dto.State;
import lombok.*;
@Entity
@Table(name="drone_models")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DroneModel {
	@Id
	@Enumerated(EnumType.STRING)
    ModelType model;
	int weight;
	
}
