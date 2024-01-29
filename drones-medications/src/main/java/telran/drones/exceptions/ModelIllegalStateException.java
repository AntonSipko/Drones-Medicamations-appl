package telran.drones.exceptions;

@SuppressWarnings("serial")
public class ModelIllegalStateException extends IllegalStateException {
    public ModelIllegalStateException() {
		super("Model already exists");
	}
}