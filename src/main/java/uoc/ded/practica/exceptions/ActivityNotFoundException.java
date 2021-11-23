package uoc.ded.practica.exceptions;

public class ActivityNotFoundException extends DEDException {
    public ActivityNotFoundException(String actId) {
        super("Activity with id " + actId + " not found.");
    }
}
