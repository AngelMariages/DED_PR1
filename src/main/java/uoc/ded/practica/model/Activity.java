package uoc.ded.practica.model;

import uoc.ded.practica.SafetyActivities4Covid19;
import uoc.ded.practica.util.Log;
import uoc.ei.tads.CuaVectorImpl;

import java.util.Date;

public class Activity {
    private final String actId;
    private final String description;
    private final Date date;
    private final SafetyActivities4Covid19.Mode mode;
    private final int organizationId;

    private final CuaVectorImpl<Ticket> ticketQueue;
    private final int places;
    private int givenTickets = 0;

    public Activity(String actId, String description, Date date, SafetyActivities4Covid19.Mode mode, int places, int organizationId) {
        this.actId = actId;
        this.description = description;
        this.date = date;
        this.mode = mode;
        this.places = places;
        this.ticketQueue = new CuaVectorImpl<>(places);
        this.organizationId = organizationId;
    }

    public String getActId() {
        return actId;
    }

    public void enqueueATicket(Ticket ticket) {
        ticketQueue.encuar(ticket);

        givenTickets++;

        Log.d("pickATicket", "actId", actId, givenTickets);

        ticket.setSeat(givenTickets);
    }

    public Ticket pickATicket() {
        return ticketQueue.desencuar();
    }

    public int availableTickets() {
        return places - givenTickets;
    }
}
