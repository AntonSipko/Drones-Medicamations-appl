package telran.drones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import telran.drones.dto.State;

//TODO create entity Medication
@Entity
@Table(name="medication")
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class Medication {
	@Id
	@Column(name="medication_code")
	int code;
	@Column(name="medication_weight")
	int weight;
	@Column(name="medication_name")
	String name;
	
	

}
