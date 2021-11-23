package uoc.ded.practica.exceptions;

public abstract class DEDException extends Exception {
    public DEDException() {
        super();
    }

    public DEDException(String message) {
        super(message);
    }
}
