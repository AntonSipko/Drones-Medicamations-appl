package telran.drones.api;

public interface ValidationCostants {
 String MISSING_DRONE_NUMBER="Missing Drone Number";
 String WRONG_DRONE_NUMBER="Wrong Drone number";
 String DRONE_NUMBER_REGEXP="^.{0,100}$";
 String MISSING_DRONE_MODEL="Drone Model is Missing";
 String MISSING_MED_CODE="Medication code is missing";
 String MED_CODE_REGEXP="^[A-Z0-9_]*$";
 String WRONG_MED_CODE="Wrong medication code";

}
