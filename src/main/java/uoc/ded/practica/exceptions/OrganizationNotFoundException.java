package uoc.ded.practica.exceptions;

public class OrganizationNotFoundException extends DEDException {
    public OrganizationNotFoundException(int organizationId) {
        super("Organization with id: " + organizationId + " not found.");
    }
}
