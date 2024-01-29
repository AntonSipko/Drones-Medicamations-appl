package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class IllegalDronesStateException extends IllegalStateException {
public IllegalDronesStateException() {
	super(ServiceExceptionMessages.DRONE_ALREADY_EXISTS);
}
}
