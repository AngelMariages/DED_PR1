package uoc.ded.practica.model;

import uoc.ded.practica.SafetyActivities4Covid19;
import uoc.ded.practica.SafetyActivities4Covid19.Status;

import java.util.Date;

public class Record {
    private String recordId;
    private Status status;

    public Record(String recordId) {
        this.recordId = recordId;
        // When we create a Record, it's first state is always pending
        this.status = Status.PENDING;
    }

    public String getRecordId() {
        return recordId;
    }

    public Status getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return "Record{" +
                "recordId='" + recordId + '\'' +
                ", status=" + status +
                '}';
    }
}
