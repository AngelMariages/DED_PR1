package uoc.ded.practica.model;

import uoc.ded.practica.SafetyActivities4Covid19;
import uoc.ded.practica.SafetyActivities4Covid19.Status;

import java.util.Date;

public class Record {
    private final String recordId;
    private final String actId;
    private final String description;
    private final Date date;
    private final SafetyActivities4Covid19.Mode mode;
    private final Status status;
    private final int places;
    private final int organizationId;

    public Record(String recordId, String actId, String description, Date date, SafetyActivities4Covid19.Mode mode, int places, int organizationId) {
        this.recordId = recordId;
        this.actId = actId;
        this.description = description;
        this.date = date;
        this.mode = mode;
        this.places = places;
        this.organizationId = organizationId;

        // When we create a Record, it's first state is always pending
        this.status = Status.PENDING;
    }

    public String getRecordId() {
        return recordId;
    }

    public Status getStatus() {
        return status;
    }

    public Activity createActivity() {
        return new Activity(this.actId, this.description, this.date, this.mode, this.places, this.organizationId);
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordId='" + recordId + '\'' +
                ", actId='" + actId + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", mode=" + mode +
                ", status=" + status +
                ", places=" + places +
                ", organizationId=" + organizationId +
                '}';
    }
}
