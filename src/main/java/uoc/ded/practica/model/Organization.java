package uoc.ded.practica.model;

public class Organization {
    private String name;
    private String description;

    public Organization(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // We don't add setters for everything,
    // instead we use a method update to have control over the changes.
    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
