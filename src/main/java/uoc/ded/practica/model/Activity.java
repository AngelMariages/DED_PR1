package uoc.ded.practica.model;

import uoc.ded.practica.SafetyActivities4Covid19;

import java.util.Date;

public class Activity {
    private final String actId;
    private final String description;
    private final Date date;
    private final SafetyActivities4Covid19.Mode mode;
    private final int places;
    private final int organizationId;

    public Activity(String actId, String description, Date date, SafetyActivities4Covid19.Mode mode, int places, int organizationId) {
        this.actId = actId;
        this.description = description;
        this.date = date;
        this.mode = mode;
        this.places = places;
        this.organizationId = organizationId;
    }

    public String getActId() {
        return actId;
    }
}
