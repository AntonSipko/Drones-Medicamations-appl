package telran.drones.service.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ModelWeight implements Serializable{
	@Column(name="model_name")
	String name;
	@Column(name="model_weight")
	int modelWeight;
	
	

}
