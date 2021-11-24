package uoc.ded.practica.model;

import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

public class Organization {
    private String name;
    private String description;

    private final LlistaEncadenada<Activity> orgActivities = new LlistaEncadenada<>();

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

    public Iterador<Activity> getOrgActivities() {
        return orgActivities.elements();
    }

    public int getNumActivities() {
        return orgActivities.nombreElems();
    }

    public void addActivity(Activity activity) {
        orgActivities.afegirAlFinal(activity);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", orgActivities=" + orgActivities +
                '}';
    }
}
