package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;
@SuppressWarnings("serial")
public class IllegalModelStateException extends IllegalStateException {
	public IllegalModelStateException() {
		super(ServiceExceptionMessages.MODEL_ALREADY_EXISTS);
	}
	

}
